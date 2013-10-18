package com.hudomju.imagesdemo.app;

import android.app.Application;

import java.util.LinkedList;
import java.util.List;

import dagger.ObjectGraph;

public class ImagesDemoApplication extends Application {

    private ObjectGraph objectGraph;

    public synchronized ObjectGraph getObjectGraph() {
        if (objectGraph == null) {
            objectGraph = ObjectGraph.create(getModules().toArray());
        }
        return objectGraph;
    }

    public void inject(Object object) {
        getObjectGraph().inject(object);
    }

    protected List<Object> getModules() {
        List<Object> modules = new LinkedList<Object>();
        modules.add(new ActivityModule(this));
        return modules;
    }
}
