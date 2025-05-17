import Foundation
import NitroModules
import GoogleSignIn
import UIKit

class HybridNitroGoogleSso: HybridNitroGoogleSsoSpec {
    let domain: String = "com.nitro.google.sso"
    let nitroGoogleSSOImpl = NitroGoogleSSOImpl()
    
    func configure(config: NitroGoogleSSOConfig) throws {
        GIDSignIn.sharedInstance.configuration = nitroGoogleSSOImpl.getConfig(config)
    }
    
    func getCurrentUser() throws -> Promise<NitroGoogleUserInfo?> {
        return .async { [weak self] in
            guard let self else {
                throw NitroGoogleSSOError.selfNoAvailable
            }
            do {
                return try await self.nitroGoogleSSOImpl.getCurrentUser()
            } catch {
                throw error
            }
        }
    }
    
    func signIn() throws -> Promise<NitroGoogleUserInfo?> {
        return .async { [weak self] in
            guard let self else {
                throw NitroGoogleSSOError.selfNoAvailable
            }
            do {
                return try await nitroGoogleSSOImpl.signIn()
            } catch {
                throw error
            }
        }
    }
    
    func signOut() throws -> Promise<Void> {
        return .async { [weak self] in
            guard let self else {
                throw NitroGoogleSSOError.selfNoAvailable
            }
            do {
                try await self.nitroGoogleSSOImpl.signOut()
            } catch {
                throw error
            }
        }
    }
}
