# TimePicker
time picker
自定义时间选择器
年月日 时 分  不包括秒
回掉特定格式时间串<br>

#show<br>
![](https://github.com/caixingcun/picture/blob/master/timepick.gif)  


#How to<br>

To get a Git project into your build:<br>

project<br>
  build.gradle
  
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
module  <br>
  build.gradle
  
 	dependencies {
	        compile 'com.github.caixingcun:TimePicker:1.0'
	}
  
  
#use<br>
        TimePickDialog dialog = new TimePickDialog(mContext, "开始时间");<br>
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);<br>
                dialog.show();<br>
                dialog.setTimePickListener(new TimePickDialog.TimePickListener() {<br>
                    @Override<br>
                    public void choseTime(String time) {<br>
                      //todo<br>
                    }<br>
                });<br>
