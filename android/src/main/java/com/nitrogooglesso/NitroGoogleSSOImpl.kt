package com.nitrogooglesso

import android.util.Log
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialRequest.Builder
import androidx.credentials.exceptions.GetCredentialException
import com.facebook.react.bridge.ReactApplicationContext
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.margelo.nitro.nitrogooglesso.NitroGoogleSSOConfig
import com.margelo.nitro.nitrogooglesso.NitroGoogleUserInfo

class NitroGoogleSSOImpl(
    private val context: ReactApplicationContext,
    private val config: NitroGoogleSSOConfig
) {
    val credentialManager: CredentialManager = CredentialManager.create(context)

    fun getCurrentUser(): NitroGoogleUserInfo? {
        // TODO: // Implement getCurrentUser
        return null
    }

    suspend fun signIn(): NitroGoogleUserInfo {
        try {
            val signInWithGoogleOption: GetSignInWithGoogleOption = GetSignInWithGoogleOption.Builder(config.webClientId)
                .setNonce(config.nonce)
                .setHostedDomainFilter(config.hostedDomain ?: "")
                .build()

            val request: GetCredentialRequest = getRequest(signInWithGoogleOption)
            val result = credentialManager.getCredential(
                request = request,
                context = context.currentActivity ?: throw Error("No HybridNitroGoogleSSO context activity"),
            )
            val credential = result.credential
            return handleSignInResult(credential)
        } catch (e: GetCredentialException) {
            e.printStackTrace()
            throw Error(e)
        }
    }

    suspend fun oneTagSignIn(): NitroGoogleUserInfo {
        try {
            val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(config.webClientId)
                .setAutoSelectEnabled(true)
                .setNonce(config.nonce)
                .build()

            val request: GetCredentialRequest = getRequest(googleIdOption)
            
            val result = credentialManager.getCredential(
                request = request,
                context = context.currentActivity ?: throw Error("No HybridNitroGoogleSSO context activity"),
            )
            return handleSignInResult(result.credential)
        } catch (e: GetCredentialException) {
            e.printStackTrace()
            throw Error(e)
        }
    }

    suspend fun signOut(){
        val clearRequest = ClearCredentialStateRequest()
        credentialManager.clearCredentialState(request = clearRequest)
    }

    fun handleSignInResult(credential: Credential): NitroGoogleUserInfo {
        return when(credential){
            is CustomCredential -> {
                if (credential.type != GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    throw Error("Received an invalid credential")
                }
                try {
                    val googleIdTokenCredential = GoogleIdTokenCredential
                        .createFrom(credential.data)
                    return NitroGoogleUserInfo(
                        email = googleIdTokenCredential.id,
                        idToken = googleIdTokenCredential.idToken,
                        displayName = googleIdTokenCredential.displayName,
                        givenName = googleIdTokenCredential.givenName,
                        familyName = googleIdTokenCredential.familyName,
                        phoneNumber = googleIdTokenCredential.phoneNumber,
                        profilePictureUri = googleIdTokenCredential.profilePictureUri.toString()
                    )
                } catch (e: GoogleIdTokenParsingException) {
                    Log.e(TAG, "Received an invalid google id token response", e)
                    throw Error(e)
                }
            }
            else -> throw Error("Received an invalid credential")
        }
    }

    companion object {
        const val TAG = "NitroGoogleSSOImpl"

        private fun getRequest(option: GetSignInWithGoogleOption): GetCredentialRequest {
            return Builder()
                .addCredentialOption(option)
                .build()
        }

        private fun getRequest(option: GetGoogleIdOption): GetCredentialRequest {
            return Builder()
                .addCredentialOption(option)
                .build()
        }
    }
}