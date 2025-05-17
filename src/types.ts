/**
 *  
 */
export enum NitroGoogleSSOConfigType {
    GOOGLE_ID = 'google_id',
    
}


export type NitroGoogleSSOConfig = {
    /**
     * The scopes are the permissions you are requesting from the user.
     * Note: Currently works only for iOS.
     * @example ['email', 'profile']
     */
    // scopes: string[];
    /**
     * The nonce is a random string that is used to prevent replay attacks.
     * It is recommended to use a random string for each sign in request.
     */
    nonce?: string
    /**
     * The iOS client ID. 
     * @example '1234567890-abcdefghijklmnopqrstuvwxyz.apps.googleusercontent.com'
     */
    iosClientId: string;
    /**
     * The web client ID is the same as serverClientId in the Google Cloud Console
     * @example '1234567890-abcdefghijklmnopqrstuvwxyz.apps.googleusercontent.com'
     */
    webClientId: string;

    /**
     * The hosted domain is the domain that the user is part of.
     * @example 'example.com'
     */
    hostedDomain?: string;
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
