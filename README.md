# React Native Nitro Google SSO

![React Native Nitro Google SSO Banner](https://github.com/user-attachments/assets/e35a76b0-a408-4666-999a-024d162f05fa)

A Simple React Native module for implementing Google Sign-In (SSO) functionality in your React Native applications uses Google Sign-In SDK on iOS and Credential Manager on Android.


## Requirements

- React Native v0.78.0 or higher
- Node 18.0.0 or higher

> [!IMPORTANT]  
> To Support `Nitro Views` you need to install React Native version v0.78.0 or higher.

## Installation

```bash
bun add react-native-nitro-google-sso react-native-nitro-modules
# or
npm install react-native-nitro-google-sso
# or
yarn add react-native-nitro-google-sso
```

## Prerequisites

Before using this module, you need to:

1. Set up a Google Cloud Project
2. Configure OAuth 2.0 credentials
3. Get your iOS Client ID and Web Client ID from the Google Cloud Console

## Configuration

First, you need to configure the module with your Google credentials. It's recommended to do this in your app's entry point:

```typescript
import NitroGoogleSSO from 'react-native-nitro-google-sso'

// Configure the module
NitroGoogleSSO.configure({
  iosClientId: 'YOUR_IOS_CLIENT_ID',
  webClientId: 'YOUR_WEB_CLIENT_ID',
  nonce: 'YOUR_NONCE', // Optional security nonce
})
```

## Usage

### Sign In

To sign in with Google:

```typescript
try {
  const user = await NitroGoogleSSO.signIn()
  if (user) {
    console.log('User signed in:', user)
  } else {
    console.log('User cancelled sign in')
  }
} catch (error) {
  console.error('Sign in error:', error)
}
```

### Get Current User

To get the currently signed-in user's information:

```typescript
try {
  const user = await NitroGoogleSSO.getCurrentUser()
  if (user) {
    console.log('Current user:', user)
  } else {
    console.log('No user is signed in')
  }
} catch (error) {
  console.error('Error getting current user:', error)
}
```

### Sign Out

To sign out the current user:

```typescript
try {
  await NitroGoogleSSO.signOut()
  console.log('User signed out successfully')
} catch (error) {
  console.error('Sign out error:', error)
}
```

## Types

### NitroGoogleSSOConfig

```typescript
interface NitroGoogleSSOConfig {
  iosClientId: string
  webClientId: string
  nonce?: string
  hostedDomain?: string
}
```

### NitroGoogleUserInfo

The user information returned by the sign-in process. The exact structure depends on the information requested during the sign-in process.

## Platform Support

This module supports both iOS and Android platforms.

## Error Handling

Always wrap the module's methods in try-catch blocks as they may throw errors in various scenarios:

- Network connectivity issues
- Invalid configuration
- User cancellation
- Authentication failures

## Best Practices

1. Configure the module as early as possible in your app's lifecycle
2. Handle all potential errors appropriately
3. Implement proper state management for the user's authentication status
4. Store sensitive credentials securely
5. Implement proper error recovery mechanisms

## Credits

Bootstrapped with [create-nitro-module](https://github.com/patrickkabwe/create-nitro-module).

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License

MIT
