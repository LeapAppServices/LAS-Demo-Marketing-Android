# LAS-Demo-Marketing-Android

## 简介

Auth 是 LAS SDK 的一个 Sample，该项目依赖于 LAS 的基础模块。通过该应用你可以学习和了解 基于 LAS SDK 的第三方操作。

## 效果

![capture](capture/marketing.gif)

## 使用

1. 打开 Android Studio 或 IDEA ，点击菜单项 `File -> Open ` 选择 `setting.gradle` 文件导入工程
2. 打开 `App.java` 文件，使用你自己的 `APP Id` ，`API KEY` 替换该文件中已定义的同名常量。
3. 确保应用完全关闭后，在 Console 上配置 Marketing 消息后重新开启应用可以查看当前匹配的消息
4. 点击 ActionBar 上的 `Test Mode` 按钮可以显示从服务器上取得的所有消息的列表

## 自定义

### Marketing

#### 显示 Marketing 消息

1. 需要显示 Marketing 消息的 Activity 必须继承自 FragmentActivity

2. 在 `onResume()` 中调用

```java
LASMarketing.setInAppMessageDisplayActivity(this);`。
```

#### 关闭 Marketing 消息

```java
LASMarketing.dismissCurrentInAppMessage();
LASMarketing.clearInAppMessageDisplayActivity();
```

#### 打开测试模式

测试模式可以显示所有从服务器取到的消息列表，方便用户对界面效果进行各种调整，但是在应用正式发布时请删除相关代码

在 `AndroidManifest.xml` 中添加如下代码

```java
<activity android:name="as.leap.internal.marketing.TestMessageListActivity"/>
```

在需要打开测试模式时调用如下代码

```java
LASMarketing.openTestMode(this);
```

#### 配置关闭按钮

默认关闭按钮显示在右边，如果需要更改到左边，可以添加如下代码

```java
LASMarketing.setInAppDismissButtonLocation(LASMarketing.InAppMessageDismissButtonLocation.LEFT);
```

如果对默认的关闭按钮的显示效果不满意，也可以使用如下代码自定义关闭按钮

```java
LASMarketing.setInAppMessageDismissButtonImage(bitmap);
```

### Push

#### 配置权限

在使用 Push 之前，必须在 `AndroidManifest.xml` 中添加如下权限

```xml
<!-- your package -->
<permission
    android:name="yourPackageName.permission.C2D_MESSAGE"
    android:protectionLevel="signature" />
<uses-permission android:name="yourPackageName.permission.C2D_MESSAGE" />

<!-- App receives GCM messages. -->
<uses-permission android:name="com.google.android.c2dm.permission.RECEIVE" />
<!-- GCM requires a Google account. -->
<uses-permission android:name="android.permission.GET_ACCOUNTS" />
<!-- Keeps the processor from sleeping when a message is received. -->
<uses-permission android:name="android.permission.WAKE_LOCK" />

<application ...>
    <!-- play services -->
    <meta-data
        android:name="com.google.android.gms.version"
        android:value="@integer/google_play_services_version" />

    <receiver
    android:name="as.leap.push.GcmBroadcastReceiver"
    android:permission="com.google.android.c2dm.permission.SEND">
    <intent-filter>
        <action android:name="com.google.android.c2dm.intent.RECEIVE" />
        <action android:name="com.google.android.c2dm.intent.REGISTRATION" />

        <category android:name="yourPackageName" />
    </intent-filter>
    </receiver>
</application>
```

请使用应用的包名替换上述 xml 中的 `yourPackageName`。

#### 配置 SenderId

使用想同时使用多个 senderId，可以使用 "," 进行连接（如：`android:value="id:senderId01,senderId02,sender03"`）

```xml
<meta-data
    android:name="as.leap.push.gcm_sender_id"
    android:value="id:senderId01" />
```

#### 配置 Notification 的图标

如果不进行配置的话，SDK 将默认采用应用的图标作为 Notification 的图标进行显示

```xml
<meta-data
    android:name="as.leap.push.notification_icon"
    android:resource="@android:drawable/ic_dialog_alert" />
```

#### 配置 PushReceiver

PushReceiver 用于处理 Push 消息及显示 Notification

```xml
<receiver android:name="as.leap.LASPushBroadcastReceiver" android:exported="false">
    <intent-filter>
        <action android:name="as.leap.push.intent.RECEIVE"/>
        <action android:name="as.leap.push.intent.OPEN"/>
    </intent-filter>
</receiver>
```

#### 自定义 PushReceiver

如果觉得默认的 PushReceiver 无法满足你的要求，可以继承 LASPushBroadcastReceiver，使用你自定义的 PushReceiver 来替换 SDK 默认的 Receiver。

AndroidManifest.xml

```xml
<receiver
    android:name=".CustomPushReceiver"
    android:exported="false">
    <intent-filter>
        <action android:name="as.leap.push.intent.RECEIVE" />
        <action android:name="as.leap.push.intent.OPEN" />
    </intent-filter>
</receiver>
```

Java

```java
public class CustomPushReceiver extends LASPushBroadcastReceiver {
}
```

使用自定义的 PushReceiver 后，你可以通过重写父类的方法来自定义对 Push 的各种处理。

##### 自定义点击 Notification 后跳转到的 Activity

```java
protected Class<? extends Activity> getActivity(Intent intent)
```

返回非 null 值后，点击 Notification 后会自动跳转到指定的 Activity，在跳转到的 Activity 中可以通过 `getIntent()` 得到该条 Push 所携带的信息

```java
Intent intent = getIntent();
if (intent != null && intent.getExtras() != null) {
    for (String key : intent.getExtras().keySet()) {
        LASLog.i(TAG, key + " = " + intent.getStringExtra(key));
    }
}
```

#####  自定义点击 Notification 后跳转的 Uri

```java
protected Uri getUri(Intent intent)
```

返回非 null 值后，点击 Notification 后会自动跳转到指定的 Uri

注意：getActivity() 的优先级要高于 getUri()。如果 getActivity（）没有返回 null 的话，则 getUri() 会被忽略

##### 自定义 Notification 的 LargeIcon

```java
 protected Bitmap getLargeIcon(Context context)
```

##### 自定义 Notificatioin 的 SmallIcon

```java
protected int getSmallIconId(Context context)
```

或者如之前所述，在 `AndroidManifest.xml` 中配置

```xml
<meta-data
    android:name="as.leap.push.notification_icon"
    android:resource="@android:drawable/ic_dialog_alert" />
```

##### 修改 Intent

如果希望修改点击 Notification 获得的 Intent 的信息（如 Intent 的 Flag），可以重写如下代码

```java
@Override
protected void startIntent(Context context, Intent intent) {
	// 修改 Intent 的 Flag 信息
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    super.startIntent(context, intent);
}
```

##### 完全自定义 Notification

如果希望自己创建 Notification 对象，可以重写如下方法

```java
protected Notification getNotification(Context context, Intent intent)
```




