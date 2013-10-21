package com.hudomju.imagesdemo.anim;

public interface Zoomer {

    public interface ZoomInCallback {

        public void onZoomInCompleted();

    }

    public void zoomImageFromThumb(ZoomInCallback zoomInCallback);

    public void zoomImageOut();

}
