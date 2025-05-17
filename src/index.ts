import { NitroModules } from 'react-native-nitro-modules'
import type { NitroGoogleSso as NitroGoogleSsoSpec } from './specs/nitro-google-sso.nitro'
export type * from './types'

const NitroGoogleSso =
    NitroModules.createHybridObject<NitroGoogleSsoSpec>('NitroGoogleSso')


export default NitroGoogleSso