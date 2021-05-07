# OtpView

Add like :

        allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
Add dependency :

 	 dependencies {
	        implementation 'com.github.GDMistry:OtpView:Tag'
	}
  
Use as :

        binding.otpView.setCount(6)
        binding.otpView.setEditTextHint("0")
        binding.otpView.setEditTextSize(43f)
        binding.otpView.setOtpText("232233")
        binding.otpView.setCursorVisible()
