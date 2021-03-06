package com.example.ovman.appbotox;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.ovman.appbotox.model3D.services.SceneLoader;
import com.example.ovman.appbotox.model3D.view.ModelSurfaceView;
import com.example.ovman.appbotox.util.Utils;
import com.example.ovman.appbotox.util.content.ContentUtils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class ZonesActivity extends Activity {

    private ImageButton back;

    //From Android-3D-model-viewer app
    private static final int REQUEST_CODE_OPEN_FILE = 1000;

    private String paramAssetDir;
    private String paramAssetFilename;
    /**
     * The file to load. Passed as input parameter
     */
    private String paramFilename;
    /**
     * Background GL clear color. Default is light gray
     */
    //private float[] backgroundColor = new float[]{0.2f, 0.2f, 0.2f, 1.0f};

    //Parte de otra clase
    private ModelSurfaceView gLView;

    private SceneLoader scene;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zones);

        back = (ImageButton)findViewById(R.id.backZonesButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /**
         * De Model Activity en android-3d-model
         */

        // Try to get input parameters
        Bundle b = getIntent().getExtras();
        if (b != null) {
            this.paramAssetDir = b.getString("assetDir");
            this.paramAssetFilename = b.getString("assetFilename");
            this.paramFilename = b.getString("uri");
            Log.i("ZonesBundle", "Dir es: "+ b.getString("assetDir"));
            //Immersive es pantalla completa
            //this.immersiveMode = "true".equalsIgnoreCase(b.getString("immersiveMode"));
            //try{
                //String[] backgroundColors = b.getString("backgroundColor").split(" ");
                //backgroundColor[0] = Float.parseFloat(backgroundColors[0]);
                //backgroundColor[1] = Float.parseFloat(backgroundColors[1]);
                //backgroundColor[2] = Float.parseFloat(backgroundColors[2]);
                //backgroundColor[3] = Float.parseFloat(backgroundColors[3]);
            //}catch(Exception ex){
                // Assuming default background color
            //}
        }
        Log.i("Renderer", "Params: assetDir '" + paramAssetDir + "', assetFilename '" + paramAssetFilename + "', uri '"
                + paramFilename + "'");

        handler = new Handler(getMainLooper());

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        gLView = new ModelSurfaceView(this);
        setContentView(gLView);

        // Create our 3D sceneario
        /**if (paramFilename == null && paramAssetFilename == null) {
            scene = new ExampleSceneLoader(this);
        } else {
            scene = new SceneLoader(this);
        }*/
        scene = new SceneLoader(this);
        scene.init();

        // Show the Up button in the action bar.
        setupActionBar();

        // TODO: Alert user when there is no multitouch support (2 fingers). He won't be able to rotate or zoom for
        // example
        Utils.printTouchCapabilities(getPackageManager());

        setupOnSystemVisibilityChangeListener();
    }
    /**
     * Methods de Model Activity
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        // getActionBar().setDisplayHomeAsUpEnabled(true);
        // }
    }

    /**Para viajar entre los modelos
     * Modelos de prueba:
     * ToyPlane (1)
     * Ship (2)
     * Teapot (3)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.model, menu);
        return true;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupOnSystemVisibilityChangeListener() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                // Note that system bars will only be "visible" if none of the
                // LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                    // TODO: The system bars are visible. Make any desired
                    // adjustments to your UI, such as showing the action bar or
                    // other navigational controls.
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        hideSystemUIDelayed(3000);
                    }
                } else {
                    // TODO: The system bars are NOT visible. Make any desired
                    // adjustments to your UI, such as hiding the action bar or
                    // other navigational controls.
                }
            }
        });
    }

    /*@Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if (immersiveMode) hideSystemUIDelayed(5000);
            }
        }
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(ZonesActivity.this.getApplicationContext(), ZonesActivity.class);
        Bundle b = new Bundle();
        switch (item.getItemId()) {

            case R.id.model_1:
                b.putString("assetDir", "models");
                b.putString("assetFilename", "ToyPlane.obj");
                b.putString("immersiveMode", "true");
                intent.putExtras(b);
                Log.i("MenuBundle", "Menu bundle es: "+b);
                startActivity(intent);
                finish();
                break;
            case R.id.model_2:
                b.putString("assetDir", "models");
                b.putString("assetFilename", "ship.obj");
                b.putString("immersiveMode", "true");
                intent.putExtras(b);
                Log.i("MenuBundle", "Menu bundle es: "+b);
                startActivity(intent);
                finish();
                break;
            case R.id.model_3:
                b.putString("assetDir", "models");
                b.putString("assetFilename", "teapot.obj");
                b.putString("immersiveMode", "true");
                intent.putExtras(b);
                Log.i("MenuBundle", "Menu bundle es: "+b);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void hideSystemUIDelayed(long millis) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        handler.postDelayed(new Runnable() {
            public void run() {
                hideSystemUI();
            }
        }, millis);
    }

    private void hideSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            hideSystemUIKitKat();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            hideSystemUIJellyBean();
        }
    }

    // This snippet hides the system bars.
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void hideSystemUIKitKat() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        final View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void hideSystemUIJellyBean() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            return;
        }
        final View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LOW_PROFILE);
    }

    // This snippet shows the system bars. It does this by removing all the flags
    // except for the ones that make the content appear under the system bars.
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void showSystemUI() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            return;
        }
        final View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    public File getParamFile() {
        return getParamFilename() != null ? new File(getParamFilename()) : null;
    }

    public String getParamAssetDir() {
        return paramAssetDir;
    }

    public String getParamAssetFilename() {
        return paramAssetFilename;
    }

    public String getParamFilename() {
        return paramFilename;
    }

    /**public float[] getBackgroundColor(){
        return backgroundColor;
    }*/

    public SceneLoader getScene() {
        return scene;
    }

    public ModelSurfaceView getgLView() {
        return gLView;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_OPEN_FILE:
                if (resultCode == RESULT_OK) {
                    // The URI of the selected file
                    final Uri uri = data.getData();
                    Log.i("Menu", "Loading '" + uri.toString() + "'");
                    if (uri != null) {
                        final String path = ContentUtils.getPath(getApplicationContext(), uri);
                        if (path != null) {
                            try {
                                scene.loadTexture(null, new URL("file://"+path));
                            } catch (MalformedURLException e) {
                                Toast.makeText(getApplicationContext(), "Problem loading texture '" + uri.toString() + "'",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Problem loading texture '" + uri.toString() + "'",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Result when loading texture was '" + resultCode + "'",
                            Toast.LENGTH_SHORT).show();
                }
        }
    }
}
