# 升级清单

- **更新时间：2017-10-18**
- **版本名：5.0.8-bugfix**
- **版本号：35**
- **修改内容：**
```
1.添加构造方法：BBKAlertDialog.Builder(Context context, int themeResId)
```

- **更新时间：2017-08-25**
- **版本名：4.0.4-bugfix**
- **版本号：24**
- **修改内容：**
```
1.获取/释放音频焦点的工具类:AudioFocusUtils;
2.修改获取文件大小的bug（改成当路径传文件夹路径时，返回-1）
```

- **更新时间：2017-08-15**
- **版本名：4.0.6-bugfix**
- **版本号：23**
- **修改内容：**
```
1.通过反射，添加系统定制弹框的几个方法：BBKAlertDialog
```

- **更新时间：2017-07-03**
- **版本名：4.0.4-bugfix**
- **版本号：22**
- **修改内容：**     
		1.添加获取OTG位置的方法        
		2.添加启用/禁用系统组件的方法       
		3.修改计算剩余磁盘空间, int类型长度不够的bug


---
- **更新时间：2017-06-28**
- **版本名：4.0.3-bugfix**
- **版本号：21**
- **修改内容：**     
		1.SharedPreferenceUtils传递null值会异常的bug, 添加容错         
		2.完善机型是否有SD卡的判断

---
- **更新时间：2017-06-13**
- **版本名：4.0.1-bugfix**
- **版本号：20**
- **修改内容：**     
		1.增加获取公司机器存取信息的工具类    
		2.添加获取机器IP的方法    
		3.完善删除媒体库记录的方法

---
- **更新时间：2017-05-31**
- **版本名：4.0.0**
- **版本号：19**
- **修改内容：**
		1.添加判定指定位置磁盘空间是否足够的方法
		2.获取设备型号中, 空格被去掉的bug

---
- **更新时间：2017-05-12**
- **版本名：3.0.4-bugfix**
- **版本号：17**
- **修改内容：**
		1.添加判定指定位置磁盘空间是否足够的方法
---
- **更新时间：2017-04-27**
- **版本名：3.0.2-bugfix**
- **版本号：15**
- **修改内容：**
		1.NetUtils判断网络是否连接的方法, 可能报NullPoint的bug

---
- **更新时间：2017-03-13**
- **版本名：3.0.0**
- **版本号：14**
- **修改内容：**
		1.添加对屏幕亮度修改的方法
		2.Toast在修改系统系统后,toast字体没变化的bug


---
- **更新时间：2017-02-16**
- **版本名：2.2.0**
- **版本号：13**
- **修改内容：**
		1.修改使用说明
		2.代码方便未做修改,只是升级版本号