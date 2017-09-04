# RoundRectImageView #

[[![](https://jitpack.io/v/chencanlin/RoundRectImageView.svg)](https://jitpack.io/#chencanlin/RoundRectImageView)]([![](https://jitpack.io/v/chencanlin/RoundRectImageView.svg)](https://jitpack.io/#chencanlin/RoundRectImageView) "How to")

**STEP 1**  Add it in your root build.gradle at the end of repositories:

	    	allprojects {
			repositories {
				...
				maven { url 'https://jitpack.io' }
			}
		}

**STEP 2** Add the dependency

    dependencies {
	        compile 'com.github.chencanlin:RoundRectImageView:1.2.0'
	}

**效果预览**

![](http://i.imgur.com/qh27iEd.png)


**使用**

    <com.org.ccl.roundrectimageview.view.RoundRectImageView
            android:id="@+id/rriv_two"
            android:layout_width="100dp"
            android:layout_height="100dp"
            android:src="@drawable/image"
            app:xradius="50dp"
            app:yradius="50dp"
            android:layout_gravity="center"
            android:layout_marginTop="20dp"/>
