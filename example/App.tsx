import React, {useEffect, useState} from 'react';
import {Alert, Button, StyleSheet, Text, View} from 'react-native';
import NitroGoogleSSO, {
  type NitroGoogleUserInfo,
} from 'react-native-nitro-google-sso';

const iosClientId = process.env.GID_CLIENT_ID || '';
const webClientId = process.env.GID_SERVER_CLIENT_ID || '';

NitroGoogleSSO.configure({
  iosClientId,
  webClientId,
  nonce: '1234567890',
});

function App(): React.JSX.Element {
  const [user, setUser] = useState<NitroGoogleUserInfo | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const checkUser = async () => {
      try {
        const user = await NitroGoogleSSO.getCurrentUser();
        setUser(user);
      } catch (error) {
        console.log('error', error);
      } finally {
        setIsLoading(false);
      }
    };
    checkUser();
  }, []);

  return (
    <View style={styles.container}>
      <Text style={styles.text}>Google SSO</Text>
      {isLoading ? (
        <Text>Loading...</Text>
      ) : (
        user && (
          <>
            <Text>{user?.displayName}</Text>
            <Text>{user?.email}</Text>
          </>
        )
      )}

      {user && !isLoading ? (
        <Button
          title="Logout"
          onPress={async () => {
            Alert.alert(
              'Logout',
              'Are you sure you want to logout? Logout will remove the user from the app and the user will need to login again.',
              [
                {text: 'Cancel', style: 'destructive'},
                {
                  text: 'Logout',
                  onPress: async () => {
                    await NitroGoogleSSO.signOut();
                    setUser(null);
                  },
                },
              ],
            );
          }}
        />
      ) : (
        <Button
          title="Login"
          onPress={async () => {
            try {
              setIsLoading(true);
              const user = await NitroGoogleSSO.signIn();
              setUser(user);
            } catch (error) {
              console.log('error', error);
            } finally {
              setIsLoading(false);
            }
          }}
        />
      )}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  text: {
    fontSize: 40,
    color: 'green',
  },
});

export default App;
