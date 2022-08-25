import 'package:flutter/material.dart';

import 'dart:async';
import 'dart:io' show Platform;
import 'package:flutter/services.dart';
import 'package:beacons_plugin/beacons_plugin.dart';


void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key, required this.title}) : super(key: key);

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _counter = 0;

  void _startSetup() async {
    // if you need to monitor also major and minor use the original version and not this fork
    BeaconsPlugin.addRegion("myBeacon", "01022022-f88f-0000-00ae-9605fd9bb620")
        .then((result) {
      print(result);
    });

    //Send 'true' to run in background [OPTIONAL]
    await BeaconsPlugin.runInBackground(true);

    //IMPORTANT: Start monitoring once scanner is setup & ready (only for Android)
    if (Platform.isAndroid) {
      BeaconsPlugin.channel.setMethodCallHandler((call) async {
        if (call.method == 'scannerReady') {
          await BeaconsPlugin.startMonitoring();
        }
      });
    } else if (Platform.isIOS) {
      await BeaconsPlugin.startMonitoring();
    }
  }

  void _startScan(){
    final StreamController<String> beaconEventsController = StreamController<String>.broadcast();
    BeaconsPlugin.listenToBeacons(beaconEventsController);

    beaconEventsController.stream.listen(
            (data) {
          if (data.isNotEmpty) {
            setState(() {
              _beaconResult = data;
            });
            print("Beacons DataReceived: " + data);
          }
        },
        onDone: () {},
        onError: (error) {
          print("Error: $error");
        });
  }

  void _stopScan() async{
    await BeaconsPlugin.stopMonitoring();
  }

  void _runInBackground() async{
    //Send 'true' to run in background
    await BeaconsPlugin.runInBackground(true);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            const Text(
              'You have pushed the button this many times:',
            ),
            Text(
              '$_counter',
              style: Theme.of(context).textTheme.headline4,
            ),
          ],
        ),
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
}
