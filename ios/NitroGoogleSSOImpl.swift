//
//  NitroGoogleSSO.swift
//  NitroGoogleSso
//
//  Created by Patrick Kabwe on 14/05/2025.
//

import Foundation
import GoogleSignIn

final class NitroGoogleSSOImpl {
    let domain: String = "com.nitro.google.sso"
    
    @MainActor
    func getCurrentUser() async throws -> Variant_NullType_NitroGoogleUserInfo {
        return try await withCheckedThrowingContinuation { continuation in
            if let user = GIDSignIn.sharedInstance.currentUser {
                continuation.resume(returning: NitroGoogleSSOImpl.mapUserInfo(from: user))
            } else {
                GIDSignIn.sharedInstance.restorePreviousSignIn { user, error in
                    if let error = error {
                        continuation.resume(throwing: error)
                    } else if let user = user {
                        continuation.resume(returning: NitroGoogleSSOImpl.mapUserInfo(from: user))
                    } else {
                        continuation.resume(returning: .first(.null))
                    }
                }
            }
        }
    }
    
    @MainActor
    func signIn() async throws -> Variant_NullType_NitroGoogleUserInfo {
        let vc: UIViewController = try self.getRootViewController()
        
        let signInResult = try await GIDSignIn.sharedInstance.signIn(withPresenting: vc)
        return NitroGoogleSSOImpl.mapUserInfo(from: signInResult.user)
    }
    
    @MainActor
    func signOut() throws {
        GIDSignIn.sharedInstance.signOut()
    }
    
}

// Utils
extension NitroGoogleSSOImpl {
    func getConfig(_ config: NitroGoogleSSOConfig) -> GIDConfiguration {
        return GIDConfiguration(
            clientID: config.iosClientId,
            serverClientID: config.webClientId,
            hostedDomain: config.hostedDomain,
            openIDRealm: nil
        )
    }
    
    private static func mapUserInfo(from user: GIDGoogleUser) -> Variant_NullType_NitroGoogleUserInfo {
        let profile = user.profile
        let profilePicUrl = profile?.imageURL(withDimension: 320)?.absoluteString
        
        return .second(
            NitroGoogleUserInfo(
                email: profile?.email ?? "",
                idToken: user.idToken?.tokenString ?? "",
                givenName: profile?.givenName,
                familyName: profile?.familyName,
                phoneNumber: nil,
                displayName: profile?.name,
                profilePictureUri: profilePicUrl
            )
        )
    }
    
    func getRootViewController() throws -> UIViewController {
        guard let windowScene = UIApplication.shared
            .connectedScenes
            .first(where: { $0.activationState == .foregroundActive }) as? UIWindowScene,
              let rootVC = windowScene
            .windows
            .first(where: { $0.isKeyWindow })?
            .rootViewController else {
            throw NSError(domain: domain, code: 0, userInfo: nil)
        }
        return rootVC
    }
}


enum NitroGoogleSSOError: Error {
    case invalidResponse
    case invalidToken
    case selfNoAvailable
}
