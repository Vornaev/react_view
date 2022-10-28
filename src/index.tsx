import {
  requireNativeComponent,
  UIManager,
  Platform,
  ViewStyle,
} from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-react-view' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

type ReactViewProps = {
  color: string;
  style: ViewStyle;
};


const ComponentName = 'TestFragmentManager';


export const ReactViewView =
  UIManager.getViewManagerConfig(ComponentName) != null
    ? requireNativeComponent<ReactViewProps>(ComponentName)
    : () => {
        throw new Error(LINKING_ERROR);
      };
