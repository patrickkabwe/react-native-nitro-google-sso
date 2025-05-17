import { type HybridObject } from 'react-native-nitro-modules';
import type { NitroGoogleSSOConfig, NitroGoogleUserInfo } from '../types';

export interface NitroGoogleSso extends HybridObject<{ ios: 'swift', android: 'kotlin' }> {
    /**
     * Configure the Google SSO. call this before signIn ideally in the app entry point
     * @param config
     * @example
     * NitroGoogleSso.configure({
     *     iosClientId: '1234567890',
     *     webClientId: '1234567890',
     *     nonce: '1234567890',
     * });
     */
    configure(config: NitroGoogleSSOConfig): void;
    /**
     * Sign in with Google
     * @returns user info or null if the user is not signed in
     * @example
     * const user = await NitroGoogleSso.signIn();
     * console.log(user);
     */
    signIn(type?: 'button' | ''): Promise<NitroGoogleUserInfo | null>;
    /**
     * Sign out from Google
     * @example
     * await NitroGoogleSso.signOut();
     */
    signOut(): Promise<void>;
    /**
     * Get the user info. call this only after signIn otherwise it will throw an error
     * @example
     * const user = await NitroGoogleSso.getCurrentUser();
     */
    getCurrentUser(): Promise<NitroGoogleUserInfo | null>;
}