import 'package:flutter/material.dart';
import 'package:tbk_plugin/nautilus.dart' as nautilus;

class OpenByPage extends StatefulWidget {
  @override
  _OpenByPageState createState() => _OpenByPageState();
}

class _OpenByPageState extends State<OpenByPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("OpenByPage"),
      ),
      body: ListView(
        children: <Widget>[
          FlatButton(
            onPressed: _openItemDetail,
            child: Text("打开淘宝详情"),
          ),
          FlatButton(
            onPressed: _openUrl,
            child: Text("打开Url"),
          )
        ],
      ),
    );
  }

  _openItemDetail() {

    Map<String, String> taoKeParamsextraParams = new Map();
    taoKeParamsextraParams['taokeAppkey'] = '24900413';

    Map<String, String> extraParams = new Map();
    extraParams['isv_code'] = 'appisvcode';


    nautilus.openItemDetail(
      itemID: "591587602964",
      taoKeParams: nautilus.TaoKeParams(
          unionId: "",
          subPid: "mm_114747138_45538443_624654015",
          pid: "mm_114747138_45538443_624654015",
          adzoneId: "624654015",
          extParams:taoKeParamsextraParams
      ),
        openType:nautilus.OpenType.NATIVE,
        schemeType:"taobao_oscheme",
        extParams:extraParams

    );
  }

  _openUrl() {
    nautilus.openUrl(
        pageUrl:
        "https://taoquan.taobao.com/coupon/unify_apply.htm?sellerId=2165762428&activityId=5698d91c0b474d9caf88279009bda4f3",
        taoKeParams: nautilus.TaoKeParams(
            unionId: "",
            subPid: "mm_26632322_6858406_23810104",
            pid: "mm_26632322_6858406_23810104",
            adzoneId: "57328044"));
  }
}
