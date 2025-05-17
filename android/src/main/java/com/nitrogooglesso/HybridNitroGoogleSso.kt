package com.nitrogooglesso

import com.margelo.nitro.NitroModules
import com.margelo.nitro.core.Promise
import com.margelo.nitro.nitrogooglesso.HybridNitroGoogleSsoSpec
import com.margelo.nitro.nitrogooglesso.NitroGoogleSSOConfig
import com.margelo.nitro.nitrogooglesso.NitroGoogleUserInfo

class HybridNitroGoogleSso: HybridNitroGoogleSsoSpec() {
    val context = NitroModules.applicationContext ?: throw Error("HybridNitroGoogleSSO context not found")
    lateinit var nitroGoogleSSOImpl: NitroGoogleSSOImpl

    override fun configure(config: NitroGoogleSSOConfig) {
        nitroGoogleSSOImpl = NitroGoogleSSOImpl(context, config)
    }

    override fun signIn(): Promise<NitroGoogleUserInfo?> {
        return Promise.async {
            nitroGoogleSSOImpl.signIn()
        }
    }

    override fun signOut(): Promise<Unit> {
        return Promise.async {
            nitroGoogleSSOImpl.signOut()
        }
    }

    override fun getCurrentUser(): Promise<NitroGoogleUserInfo?> {
        return Promise.async {
            nitroGoogleSSOImpl.getCurrentUser()
        }
    }
}
