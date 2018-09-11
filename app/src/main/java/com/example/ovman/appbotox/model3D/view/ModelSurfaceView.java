package com.example.ovman.appbotox.model3D.view;

import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import com.example.ovman.appbotox.ZonesActivity;
import com.example.ovman.appbotox.model3D.controller.TouchController;

/**
 * This is the actual opengl view. From here we can detect touch gestures for example
 * 
 * @author andresoviedo
 *
 */
public class ModelSurfaceView extends GLSurfaceView {

	private ZonesActivity parent;
	private ModelRenderer mRenderer;
	private TouchController touchHandler;

	public ModelSurfaceView(ZonesActivity parent) {
		super(parent);

		// parent component
		this.parent = parent;

		// Create an OpenGL ES 2.0 context.
		setEGLContextClientVersion(2);

		// This is the actual renderer of the 3D space
		mRenderer = new ModelRenderer(this);
		setRenderer(mRenderer);

		// Render the view only when there is a change in the drawing data
		// TODO: enable this again
		// setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

		touchHandler = new TouchController(this, mRenderer);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return touchHandler.onTouchEvent(event);
	}

	public ZonesActivity getZonesActivity() {
		return parent;
	}

	public ModelRenderer getModelRenderer(){
		return mRenderer;
	}

}