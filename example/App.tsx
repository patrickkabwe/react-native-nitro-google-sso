import React, {useEffect, useState} from 'react';
import {Alert, Button, Platform, StyleSheet, Text, View} from 'react-native';
import NitroGoogleSSO, {
  type NitroGoogleUserInfo,
} from 'react-native-nitro-google-sso';

const iosClientId =
  process.env.GID_CLIENT_ID ||
  '2213441010-ocn1h0m4s73socf0md7a29d3m7plng3b.apps.googleusercontent.com';
const webClientId =
  process.env.GID_SERVER_CLIENT_ID ||
  '2213441010-vuqdede2t8vtnhs7no393qn5sjtehvu6.apps.googleusercontent.com';

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
        <>
          <View style={styles.spacer} />
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
          <View style={styles.spacer} />

          {Platform.OS === 'android' && (
            <Button
              title="One Tag Login"
              onPress={async () => {
                const user = await NitroGoogleSSO.oneTagSignIn();
                setUser(user);
              }}
            />
          )}
        </>
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
  spacer: {
    height: 10,
  },
});

export default App;
