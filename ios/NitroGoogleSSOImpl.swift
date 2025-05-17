//
//  NitroGoogleSSO.swift
//  NitroGoogleSso
//
//  Created by Patrick Kabwe on 14/05/2025.
//

import Foundation
import GoogleSignIn

class NitroGoogleSSOImpl {
    let domain: String = "com.nitro.google.sso"
    
    func getCurrentUser() async throws -> NitroGoogleUserInfo? {
        return try await withCheckedThrowingContinuation { continuation in
            if let user = GIDSignIn.sharedInstance.currentUser {
                continuation.resume(returning: mapUserInfo(from: user))
            } else {
                GIDSignIn.sharedInstance.restorePreviousSignIn { [weak self] user, error in
                    if let error = error {
                        continuation.resume(throwing: error)
                    } else if let user = user {
                        continuation.resume(returning: self?.mapUserInfo(from: user))
                    } else {
                        print("hello")
                        continuation.resume(returning: nil)
                    }
                }
            }
        }
    }

    private func mapUserInfo(from user: GIDGoogleUser) -> NitroGoogleUserInfo {
        let profile = user.profile
        let profilePicUrl = profile?.imageURL(withDimension: 320)?.absoluteString
        return NitroGoogleUserInfo(
            email: profile?.email ?? "",
            idToken: user.idToken?.tokenString ?? "",
            givenName: profile?.givenName,
            familyName: profile?.familyName,
            phoneNumber: nil,
            displayName: profile?.name,
            profilePictureUri: profilePicUrl
        )
    }
    
    @MainActor
    func signIn() async throws -> NitroGoogleUserInfo {
        let vc: UIViewController = try self.getRootViewController()
        let signInResult = try await GIDSignIn.sharedInstance.signIn(withPresenting: vc)
        
        let user = signInResult.user
        let emailAddress = user.profile?.email
        let fullName = user.profile?.name
        let givenName = user.profile?.givenName
        let familyName = user.profile?.familyName
        let idToken = user.idToken
        
        let profilePicUrl = user.profile?.imageURL(withDimension: 320)
        
        return NitroGoogleUserInfo(
            email: emailAddress ?? "",
            idToken: idToken?.tokenString ?? "",
            givenName: givenName,
            familyName: familyName,
            phoneNumber: nil,
            displayName: fullName,
            profilePictureUri: profilePicUrl?.absoluteString
        )
    }
    
    func signOut() throws {
        GIDSignIn.sharedInstance.signOut()
    }
    
}

// Utils
extension NitroGoogleSSOImpl {
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
