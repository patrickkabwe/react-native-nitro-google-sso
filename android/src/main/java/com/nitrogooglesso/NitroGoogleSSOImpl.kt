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
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.margelo.nitro.nitrogooglesso.NitroGoogleSSOConfig
import com.margelo.nitro.nitrogooglesso.NitroGoogleUserInfo

class NitroGoogleSSOImpl(
    private val context: ReactApplicationContext,
    config: NitroGoogleSSOConfig
) {
    val signInWithGoogleOption: GetSignInWithGoogleOption = GetSignInWithGoogleOption.Builder(config.webClientId)
        .setNonce(config.nonce)
        .build()

    val credentialManager: CredentialManager = CredentialManager.create(context)

    fun getCurrentUser(): NitroGoogleUserInfo {
        return NitroGoogleUserInfo(
            email = "email",
            idToken = "idToken",
            displayName = "displayName",
            givenName = "givenName",
            familyName = "familyName",
            phoneNumber = "",
            profilePictureUri = "",
        )
    }

    fun getRequest(): GetCredentialRequest {
        val request: GetCredentialRequest = Builder()
            .addCredentialOption(signInWithGoogleOption)
            .build()
        return request
    }

    suspend fun signIn(): NitroGoogleUserInfo {
        try {
            val request: GetCredentialRequest = getRequest()

            val result = credentialManager.getCredential(
                request = request,
                context = context.currentActivity ?: throw Error("No HybridNitroGoogleSSO context activity"),
            )

            val credential = result.credential
            return handleSignInResult(credential)
        } catch (e: GetCredentialException) {
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
    }
}