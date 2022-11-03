import React, { useEffect, useRef } from 'react';

import { StyleSheet, View, Text, TextInput } from 'react-native';
import { ReactViewView, TestFragmentManager } from 'react-native-react-view';
import { requireNativeComponent } from 'react-native';
import {
PixelRatio,
  UIManager,
  findNodeHandle
} from 'react-native';



const createFragment = (viewId) =>
  UIManager.dispatchViewManagerCommand(
    viewId,
    // we are calling the 'create' command
    UIManager.TestFragmentManager.Commands.create.toString(),
    [viewId]
  );



export default function App() {

   const ref = useRef(null);

   useEffect(() => {
       const viewId = findNodeHandle(ref.current);
       createFragment(viewId);
     }, []);

  return (
    <View style = {styles.container}>

      <ReactViewView style={[styles.container, {
                             transform: [{ scale: 0.8 }]
                           }]}    ref={ref}/>


  </View>

  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    marginTop:50,

  },
  reactWidget: {
    flex:0.6,
    backgroundColor: '#000'
  }
});


