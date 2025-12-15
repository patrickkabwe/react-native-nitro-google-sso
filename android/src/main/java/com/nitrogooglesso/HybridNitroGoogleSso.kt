package com.nitrogooglesso

import com.margelo.nitro.NitroModules
import com.margelo.nitro.core.Promise
import com.margelo.nitro.nitrogooglesso.HybridNitroGoogleSsoSpec
import com.margelo.nitro.nitrogooglesso.NitroGoogleSSOConfig
import com.margelo.nitro.nitrogooglesso.NitroGoogleUserInfo
import com.margelo.nitro.nitrogooglesso.Variant_NullType_NitroGoogleUserInfo
import kotlinx.coroutines.MainScope

class HybridNitroGoogleSso: HybridNitroGoogleSsoSpec() {
    val context = NitroModules.applicationContext ?: throw Error("HybridNitroGoogleSSO context not found")
    val mainScope = MainScope()
    lateinit var nitroGoogleSSOImpl: NitroGoogleSSOImpl

    override fun configure(config: NitroGoogleSSOConfig) {
        nitroGoogleSSOImpl = NitroGoogleSSOImpl(context, config)
    }

    override fun signIn(): Promise<Variant_NullType_NitroGoogleUserInfo> {
        return Promise.async(mainScope) {
            nitroGoogleSSOImpl.signIn()
        }
    }

    override fun oneTapSignIn(): Promise<Variant_NullType_NitroGoogleUserInfo> {
        return Promise.async(mainScope) {
            nitroGoogleSSOImpl.oneTapSignIn()
        }
    }

    override fun signOut(): Promise<Unit> {
        return Promise.async(mainScope) {
            nitroGoogleSSOImpl.signOut()
        }
    }

    override fun getCurrentUser(): Promise<Variant_NullType_NitroGoogleUserInfo> {
        return Promise.async(mainScope) {
            nitroGoogleSSOImpl.getCurrentUser()
        }
    }
}
