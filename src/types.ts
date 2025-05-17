
export type NitroGoogleSSOConfig = {
    scopes: string[];
    nonce?: string
    iosClientId: string;
    webClientId: string;
}

export type NitroGoogleUserInfo = {
    email: string;
    idToken: string;
    givenName?: string;
    familyName?: string;
    phoneNumber?: string;
    displayName?: string;
    profilePictureUri?: string;
}
