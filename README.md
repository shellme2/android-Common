#通用工具类
----
## 关于

- 版本: V3.0.0


## 前置条件

- API>=15

- 已经申请的权限:

		<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
		<uses-permission android:name="android.permission.CHANGE_WIFI_STATE"/>
		<uses-permission android:name="android.permission.READ_PHONE_STATE"/>
		<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
		<uses-permission android:name="android.permission.INTERNET"/>
		<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
		<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
		<uses-permission android:name="android.permission.WRITE_SECURE_SETTINGS"/>
		<uses-permission android:name="android.permission.WRITE_SETTINGS"/>
		<uses-permission android:name="android.permission.WRITE_MEDIA_STORAGE"/>


## 功能列表
参考使用说明

## 升级清单文档

- 请参考[升级清单文档](./UPDATE.md)

## 项目依赖
无

## 公共接口

类名 	| 描述
:---- 	| :----
[DeviceUtils](#%E8%AE%BE%E5%A4%87%E7%9B%B8%E5%85%B3-deviceutils)			| 获取设备相关信息;  获取IMEI,MAC地址, 内存大小等
[AppUtils](#app%E7%9B%B8%E5%85%B3-apputils)			| 获取app的相关信息; 提供判断app是否在前台运行, 获取app包名, 签名, 名称,版本号等
[StringUtils](#%E5%AD%97%E7%AC%A6%E4%B8%B2%E7%9A%84%E4%B8%80%E4%BA%9B%E5%88%A4%E6%96%AD-stringutils) 		| 对字符串的简单操作; 进行urlEncode,urlDecode, 是否匹配正则表达式
[StatusBarUtils](#%E7%8A%B6%E6%80%81%E6%A0%8F%E7%9B%B8%E5%85%B3-statusbarutils) 		| 状态栏设置;  设置状态栏标签深色/浅色, 设置状态栏颜色, 设置状态栏透明,半透明
[DisplayUtils](#%E9%A2%91%E5%B9%95%E6%98%BE%E7%A4%BA%E7%9B%B8%E5%85%B3%E7%9A%84%E4%B8%80%E4%BA%9B%E5%B7%A5%E5%85%B7-displayutils)		| 屏幕的信息;  提供获取频幕的宽度,高度,像素密度, 状态栏高度, dp和px的互转
[KeyboardUtils](#%E8%BD%AF%E9%94%AE%E7%9B%98%E7%9B%B8%E5%85%B3-keyboardutils)	 	| 对软键盘的操作; 用于判断软键盘是否打卡, 打开/关闭软键盘
[NetUtils](#%E7%BD%91%E7%BB%9C%E7%9B%B8%E5%85%B3-netutils) 			| 网络的检测;  用于判断网络是否连接, ping网络, 获取网络类型
[BBKStorageUtils](#%E5%85%AC%E5%8F%B8%E6%9C%BA%E5%99%A8%E5%AD%98%E5%82%A8%E7%9B%B8%E5%85%B3-bbkstorageutils)  	| 公司机器的存储盘相关的工具类
[FileUtils](#%E6%96%87%E4%BB%B6%E7%9B%B8%E5%85%B3-fileutils)   		| 文件操作帮助类, 用于获取uri路径, 文件操作等
[BitmapUtils](#%E5%9B%BE%E7%89%87%E7%9B%B8%E5%85%B3-bitmaputils)  		| Bitmap帮助类; eg, 获取bitmap, 灰度,旋转,模糊等
[SharedPreferenceUtils](#sharedpreference%E7%9B%B8%E5%85%B3-sharedpreferenceutils) 	| SharedPreference工具类, 提供便捷操作SharedPreference的方法
[IntentUtils](#intent%E7%9B%B8%E5%85%B3-intentutils)				| 提供各种Intent
[ToastUtils](#toast%E7%9B%B8%E5%85%B3-toastutils) 				| Toast帮助类; eg, 显示/隐藏toast
[MediaLibraryUtils](#%E5%AA%92%E4%BD%93%E5%BA%93%E7%9B%B8%E5%85%B3-medialibraryutils)		| 媒体库工具类, 提供媒体库的扫描,删除功能
[SystemStoreUtils](#%E6%9D%83%E9%99%90%E5%A3%B0%E6%98%8E%E7%9A%84%E9%85%8D%E7%BD%AE-systemstoreutils)		| 存储配置到系统设置中
[DialogUtils](#dialog%E7%9B%B8%E5%85%B3-dialogutils)				| Dialog工具类; eg, 隐藏dialog弹出动画
[ThreadUtils](#%E8%BF%9B%E7%A8%8B%E7%BA%BF%E7%A8%8B%E7%9B%B8%E5%85%B3-threadutils)			| 进程和线程相关工具类
[BBKWindowUtils](#bbkwindowutils-%E7%B3%BB%E7%BB%9Fwindow%E7%9B%B8%E5%85%B3%E6%93%8D%E4%BD%9C)		| BBK机器操控window的相关接口; eg, home键, 通知栏控制
[BBKMediaPlayer](#bbkmediaplayer-%E5%85%BC%E5%AE%B9%E7%B3%BB%E7%BB%9F%E6%8E%A5%E5%8F%A3%E7%9A%84%E6%92%AD%E6%94%BE%E5%99%A8)		| 对系统的MediaPlayer的封装, 添加额外的接口
[SystemPropertyUtils](#systempropertyutils%E7%B3%BB%E7%BB%9F%E9%A2%9D%E5%A4%96%E6%B7%BB%E5%8A%A0%E7%9A%84%E9%83%A8%E5%88%86%E5%B1%9E%E6%80%A7)	| 内部机器一些系统数据的封装
[BBKAlertDialog](#bbkalertdialog-%E5%85%BC%E5%AE%B9%E7%B3%BB%E7%BB%9F%E6%8E%A5%E5%8F%A3%E7%9A%84%E5%BC%B9%E6%A1%86%E5%9C%A8alertdialog%E7%9A%84%E5%9F%BA%E7%A1%80%E4%B8%8A%E5%8A%A0%E4%BA%86%E5%A6%82%E4%B8%8B%E5%87%A0%E4%B8%AA%E6%96%B9%E6%B3%95)       | 对系统的BBKAlertDialog的封装, 添加额外的接口

## 使用说明
### 1.添加依赖
	compile bfcBuildConfig.deps.'bfc-common'	

> 如果没添加网络配置依赖, 请先添加网络依赖,使用的[网络配置](http://172.28.2.93/bfc/Bfc/blob/develop/public-config/%E4%BE%9D%E8%B5%96%E4%BD%BF%E7%94%A8%E8%AF%B4%E6%98%8E.md), 请参考网络配置使用;
	

### 2.使用
> 通用库中包含很多工具类, 一般现有的app已有部分工具类, 或者不需要使用全部工具类; 如果不想引用,可以参考各个工具类的实现, 将代码复制过去


#### 设备相关 DeviceUtils
 <span id="DeviceUtils"/>

方法名 	| 功能描述
:----	| :----
getBbkSn				| 获取BbkSn
getSDK					| 获取系统的api版本
getIMEI					| 获取IMEI
getMac					| 获取机器Wifi的mac地址
getManufacturer			| 获取设备的制造厂商
getModel				| 获取设备型号; eg,imoo
getMachineId			| 获取机器唯一的Id
getSystemVersion		| 获取系统版本名
getMemoryAvailableSize	| 获取可用内存大小
getMemoryTotalSize		| 获取总内存大小
getCpuCoreSize			| 获取cpu的核心数
getBatteryLevel			| 获取当前电池电量
isCharge				| 判断设置是否在充电
isPhone					| 判断设备是否是手机
vibrate					| 让手机震动一下
openSetting				| 打开设置界面


#### App相关  AppUtils
 <span id="AppUtils"/>

方法名 	| 功能描述
:----	| :----
getAppName				| 获取app的名称
getVersionName			| 获取App的版本名
getVersionCode			| 获取app的版本号
isAppInstalled			| 判断指定包名的app是否安装
isSystemApp				| 判断指定包名的app是否是系统app
installApp				| 调用系统的apk安装管理器, 安装App
uninstallApp			| 调用系统的apk管理器, 卸载指定app
getSignature			| 获取app的签名数据
getSignatureMd5			| 获取指定app签名的md5值
isForeground			| 获取当前app是否在前台, 即是否有activity在最屏幕最上层
openAppInfoSettings		| 跳转到指定app, 在设置里面的应用详情界面
startApp				| 启动指定的app, 启动和桌面图标点击启动的activity相同
 

#### 字符串的一些判断 StringUtils
 <span id="StringUtils"/>

方法名 	| 功能描述
:----	| :----
isEmpty				| 判断字符串是否为null或者空字符串
isSpace				| 判断字符串是否为null获取全部是空格
encodeUrl			| 对字符串进行urlEncode
decodeUrl			| 对字符串进行urlDecode
bytes2HexString		| 将字节数组转换成16进制字符串
isMatch				| 判读字符串是否匹配正则表达式
isEmail				| 判断字符串是否是email格式
isMobileNum			| 判断字符串是否是手机号码
isIp				| 判断是否是ip地址   


#### 状态栏相关  StatusBarUtils
<span id="StatusBarUtils"/>      

方法名 	| 功能描述
:----	| :----
enableDarkMode				| 设置状态栏文字颜色, 文字为白色或者黑色
setColor					| 设置状态栏颜色  
setTransparent				| 设置状态栏透明  
setTranslucent				| 设置状态栏半透明

#### 频幕显示相关的一些工具 DisplayUtils
<span id="DisplayUtils"/>

 - 是否锁屏   
 	isScreenLocked
 - 屏幕宽度  
 	getScreenWidth
 - 屏幕高度  
 	getScreenHeight
 - 频幕dpi  
 	getDpi
 - 频幕像素密度  
 	getDensity
 - 状态栏高度  
 	getScreenHeight
 - 是否有导航栏  
 	isExistNavigationBar
 - 导航栏高度  
 	getNavigationBarHeight
 - dp转px  
 	dp2px
 - px转dp  
 	px2dp
 - sp转px
 	sp2px
 - px转sp  
 	px2sp
 - 获取屏幕截图  
 	captureScreen
 - 获取当前屏幕亮度
    getScreenBrightness
 - 获取当前屏幕亮度模式(手动,自动)
    getScreenBrightnessMode
 - 设置当前屏幕亮度模式和亮度
    setScreenBrightness

#### 软键盘相关 KeyboardUtils
<span id="KeyboardUtils"/>

方法名 	| 功能描述
:----	| :----
hideSoftKeyborad			| 关闭软键盘
showSoftKeyborad			| 打开软键盘
isSoftkeyboardShow			| 判断软键盘是否打开  

#### 网络相关 NetUtils
<span id="NetUtils"/>

方法名 	| 功能描述
:----	| :----
isConnected					| 判断网络是否连接 
isWifiConnected				| 判断wifi是否连接    
isMobileDataConnected		| 判读移动网络是否连接  
isWifiEnable				| 判断wifi是否打开  
enableWifi					| 打开/关闭wifi  
openMobileDataSettings		| 跳转到移动网络设置界面  
openWifiSetting				| 跳转到wifi设置界面  
getNetworkType				| 获取网络类型(wifi, 2G, 3G, 4G, 蓝牙)   
getIpAddress				| 获取当前网络的IP地址

    
#### 公司机器存储相关 BBKStorageUtils
<span id="BBKStorageUtils"/>

 - 获取A盘     
 	getFlashA
 - 获取A盘状态      	  
    getFlashAStorageState
 - 获取机器内置盘(机器自带存储)的的位置         	
    getExternalStorage
 - 获取机器内置盘的状态      	 
    getExternalStorageState
 - 机器是否支持SD卡(是否有sd卡槽)	  
    isSupportSDCard
 - 获取SD卡的位置     	 
    getSDCard
 - 获取SD卡的状态    	 
    getSDCardStorageState    


###### 老方法和工具类的接口对比

机器类型 	| flashA | 机器自带存储| SD Card 
:---- 	| :----| ----|-----
获取存储位置| getInternalStorageDirectory | getExternalStorageDirectory | getExternalFlashStorageDirectory
获取存储位置的状态| getInternalStorageState | getExternalStorageState | getExternalFlashStorageState
 | | | | 
工具类获取存储位置 |  getFlashA | getExternalStorage | getSDCard 
工具类获取存储位置状态 |  getFlashAStorageState | getExternalStorageState | getSDCardStorageState

> 工具类额外增加了判断是否有SD卡槽的方法 `isSupportSDCard`, 内部是按机型判断, 新机型出来时, 可能需要额外更新库;    
> 当挂载状态为 `Environment.MEDIA_MOUNTED` 时, 说明该存储位置可用;
 

#### 文件相关 FileUtils
<span id="FileUtils"/>

方法名 	| 功能描述
:----	| :----
isSpaceEnough						| 判断文件对应的磁盘可用空间是否足够
getUriReallyPath					| 获取Uri对应的本地文件路径
closeIO								| 关闭IO
isFileExists						| 判断文件是否存在
isDir								| 判断是否是一个目录
isFile								| 判断是否是一个文件
createFileOrExists					| 创建文件,如果存在就不创建
createFileByDeleteOld				| 创建文件,如果存在,则删除后重新创建
createDirOrExists					| 创建目录; 如果存在,则返回; 如果不存在则创建
deleteFile							| 删除文件
deleteDir							| 删除文件夹
readFile2Bytes						| 从文件中读取字节数据
writeFile							| 将数据写入文件
transfer							| 传输数据,将指定输入流写到输出流中  
getFileSize							| 获取文件大小
getFileExtension					| 获取文件扩展名
getDirName							| 获取文件目录名
getFileName 						| 获取文件名
getFileAvaiableSize					| 获取指定文件的可用大小空间


#### 图片相关 BitmapUtils
<span id="BitmapUtils"/>

 - 获取Bitmap  
 	getBitmap
 - 添加圆角  
 	toRoundCorner
 - 将bitmap变成圆形  
 	toRound
 - 灰度  
 	toGray
 - 旋转  
 	rotate
 - 模糊  
 	toBlur
 - 保存  
 	save


#### 时间相关  DateUtils
<span id="DateUtils"/>

方法名 	| 描述
:---- 	| :----
isLeapYear			| 判断是否是闰年
format				| 用指定的时间格式 格式化时间


#### SharedPreference相关 SharedPreferenceUtils
<span id="SharedPreferenceUtils"/>

方法名 	| 描述
:---- 	| :----
getInstance				| 获取实例
put						| 保存数据
get						| 获取数据
remove					| 删除数据
clear					| 清楚所有保存的数据
getSharedPreference		| 获取sharedPreferences对象, 用于app自己操作	

#### Intent相关 IntentUtils
<span id="IntentUtils"/>

方法名 	| 描述
:---- 	| :----
createExplicitFromImplicitIntentForServices		| 将隐士启动Services的Intent转换成明确的Intent, 在5.0之后,隐士的Intent是无法启动服务的
getPickIntentWithGallery						| 获取跳转到图库,选择照片的Intent
getPickFileIntent								| 获取选择文件的Intent, 选择图片,音频,视频等
getCaptureIntent								| 获取拍照的Intent
isActivityAvailable								| 判断指定Intent的activity是否存在
getInstallAppIntent								| 获取使用系统安装器,安装apk文件的Intent
getUninstallAppIntent							| 获取卸载指定包名app的Intent
getLaunchAppIntent								| 根据包名获取启动指定app的Intent
getAppInfoSettingsIntent						| 获取跳转到指定包名的,在设置中应用详情界面的Intent
getShareTextIntent								| 获取分享文本的Intent
getShareImageIntent								| 获取分享图片的Intent
getSendSmsIntent								| 获取发送短信的Intent
getDialIntent									| 跳转至拨号界面的Intent
getGoSettingsIntent								| 获取跳转到设置界面的Intent
getGoHomeIntent									| 跳转到桌面的Intent

> 有部分Intent, 在部分机器上并没有相关的app去处理, 用于跳转时, 需要自己确定机器上是否有app处理该Intent  
> eg. 家教机上, 没法处理打电话的Intent, 并且默认的文件管理器没法处理选择文件的Intent

#### Shell相关 ShellUtils
<span id="ShellUtils"/>

 - 判断是否root	isRoot
 - 执行shell命令		execCmd

#### Toast相关  ToastUtils
<span id="ToastUtils"/>

方法名 	| 描述
:---- 	| :----
getInstance			| 获取实例
s					| 短toast	 
l					| 长toast
cancel				| 取消toast	  


#### 媒体库相关	MediaLibraryUtils
<span id="MediaLibraryUtils"/>

 - 扫描文件       
 	scanFile
 - 添加进媒体库         
 	sendScanFileBroadcast
 - 从媒体库删除文件	      
 	deleteFromMediaLibrary


#### 权限声明的配置 SystemStoreUtils
<span id="SystemStoreUtils"/>

方法名 	| 描述
:---- 	| :----
put				| 保存数据到Setting中 
get				| 从Setting中取数据 
remove			| 删除指定数据
	
> 主要用于保存权限声明是否启动, 保存的数据在清除app数据后, 不会被清除
> 该功能因为涉及到权限问题, 在第三方手机上 无法使用

#### Dialog相关	DialogUtils
 - 取消dialog的底部弹出动画  	 
 	setNoAnimate

> 该功能只针对公司内部的手机

#### 进程/线程相关  ThreadUtils
<span id="ThreadUtils"/>

方法名 	| 描述
:---- 	| :----
getProcessName			| 获取当前进程名称 
isMainThread			| 判断是否是主线程  
runOnMainThread			| 在主线程运行任务   

#### BBKWindowUtils 系统window相关操作
方法名 	| 描述
:---- 	| :----
disableHomeKey		| 禁止使用Home键
enableHomeKey		| 允许使用Home键
disableAppSwitchKey	|	禁止使用多任务切换键
enableAppSwitchKey	| 	允许使用多任务切换键
disablePullStatusBar	| 禁止下拉通知栏
enablePullStatusBar		| 允许下拉通知栏

> 只对内部机器有效

#### BBKMediaPlayer 兼容系统接口的播放器
方法名 	| 描述
:---- 	| :----
create 	|	系统原生的MediaPlayer构建兼容的BBKMediaPlayer
setDataSource	| 兼容系统增加的设置数据源的方法
setLooping		| 兼容系统增加的, 指定时间段循环播放的方法
setPlaySpeed	| 兼容系统增加的, 设置播放速度的方法
setPlayTone		| 兼容系统增加的, 设置PlayTone的方法
enableTimedTextTrackIndex	| 兼容系统增加的 enableTimedTextTrackIndex 方法
enableTimedText		| 兼容系统增加的 enableTimedText 方法
disableTimedText	| 兼容系统增加的 disableTimedText 方法

###### BBKMediaPlayer使用和替换方法
BBKMediaPlayer是系统MediaPlayer的一个代理类, 和系统MediaPlayer有完全相同的接口, 在额外添加的系统自定义的接口;      
所以完全可以把系统MediaPlayer使用BBKMediaPlayer代替

 - 如果是完全从头使用MediaPlayer, 可以直接使用BBKMediaPlayer代替

			// 创建BBKMediaPlayer, 其他使用方法, 和系统的MediaPlayer完全一样
			BBKMediaPlayer mMediaPlayer = new BBKMediaPlayer();
			mMediaPlayer.reset();
			mMediaPlayer.setDataSource(audioData, length);
			mMediaPlayer.prepare();
			mMediaPlayer.start();

 - 如果是已经有了MediaPlayer, 可以使用 'create' 方法构建出 BBKMediaPlayer

 		BBKMediaPlayer bbkMediaPlayer = BBKMediaPlayer.create(mediaPlayer);

 - 如果仅仅是为了调用内部系统添加的方法, 可以如下

		 // 调用系统的方法, 只需先从系统的 MediaPlayer 构建出 BBKMediaPlayer, 然后调用系统添加的接口就行
		BBKMediaPlayer.create(mediaPlayer).setPlaySpeed(BBKMediaPlayer.SPEED_LEVEL.SPEED_INDEX_1_DOT_0);

    
#### SystemPropertyUtils　系统额外添加的部分属性

方法名／属性 	| 描述
:---- 	| :----   
JUNIOR_MARK		   |	系统添加的，保存在Settings.System中的小学标志位　对应的key
getJuniorMarkValue | 获取系统小学标志位对应的值


> 只对内部机器有效

#### BBKAlertDialog 兼容系统接口的弹框(在AlertDialog的基础上加了如下几个方法)
方法名／属性 	                                    | 描述
:---- 	                                           | :----
setBBKSound(Uri soundUri)	                        | 方法：设置声音
setBBKSound(int soundRes)	                        | 方法：设置声音
setBBKMessageIcon(int messageIcon)	                | 方法：设置正文的Icon
setBBKDialogStyle(DialogStyleType styleType)        | 方法：设置系统提供的固定弹框
DialogStyleType.UNINSTALL                           | 属性值：卸载应用
DialogStyleType.DELETE_SINGLE                       | 属性值：删除单个文件
DialogStyleType.DELETE_MULTIPLE                     | 属性值：删除多个
DialogStyleType.NO_NETWORK                          | 属性值：无网络
例子：

        AlertDialog dialog = new BBKAlertDialog.Builder(getContext())
                        .setBBKDialogStyle(BBKAlertDialog.DialogStyleType.UNINSTALL) //该处为定制方法,请先设置该参数
                        .setMessage("aaaaaa")
                        ……
                        .create();

        dialog.show();


#### AudioFocusUtils 获取/释放音频焦点的工具类
方法名 	| 描述
:---- 	| :----
requestAudioFocus	| 获取音频焦点
abandonAudioFocus	| 释放音频焦点
## 错误码
无


