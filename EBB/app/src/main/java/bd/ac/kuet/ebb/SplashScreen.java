package bd.ac.kuet.ebb;

import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;

import com.daimajia.androidanimations.library.Techniques;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

public class SplashScreen extends AwesomeSplash {


    @Override
    public void initSplash(ConfigSplash configSplash) {
        /* you don't have to override every property */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Customize Circular Reveal
        configSplash.setBackgroundColor(R.color.colorPrimary); //any color you want form colors.xml
        configSplash.setAnimCircularRevealDuration(500); //int ms
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);  //or Flags.REVEAL_LEFT
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM); //or Flags.REVEAL_TOP

        //Choose LOGO OR PATH; if you don't provide String value for path it's logo by default

        //Customize Logo
        configSplash.setLogoSplash(R.drawable.startlogo); //or any other drawable
        configSplash.setAnimLogoSplashDuration(1000); //int ms
        configSplash.setAnimLogoSplashTechnique(Techniques.FadeInDown);


        //Customize Path
        //configSplash.setPathSplash(Constants.DROID_LOGO); //set path String
//        configSplash.setOriginalHeight(200); //in relation to your svg (path) resource
//        configSplash.setOriginalWidth(200); //in relation to your svg (path) resource
//        configSplash.setAnimPathStrokeDrawingDuration(4000);
//        configSplash.setPathSplashStrokeSize(3); //I advise value be <5
//        configSplash.setPathSplashStrokeColor(R.color.colorPrimaryDark); //any color you want form colors.xml
//        configSplash.setAnimPathFillingDuration(3000);
//        configSplash.setPathSplashFillColor(R.color.colorPrimaryDark); //path object filling color


        //Customize Title
        configSplash.setTitleSplash("");
        configSplash.setTitleTextColor(R.color.colorPrimaryDark);
        configSplash.setTitleTextSize(20f); //float value
        configSplash.setAnimTitleDuration(2000);
        configSplash.setAnimTitleTechnique(Techniques.FadeInDown);
        configSplash.setTitleFont("");
        //configSplash.setTitleFont(); //provide string to your font located in assets/fonts/
    }

    @Override
    public void animationsFinished() {
        Intent intent = new Intent(SplashScreen.this, Login.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        //do nothing
    }
}
