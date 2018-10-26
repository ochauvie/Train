package com.och.train.listener;

import android.view.DragEvent;
import android.view.View;

public class PlanDragListener implements View.OnDragListener {

        private PlanListener listener;
        private float oldY;

    public PlanDragListener(PlanListener listener) {
            this.listener = listener;
        }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        int action = event.getAction();
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                // do nothing
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                oldY = event.getY();
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                break;
            case DragEvent.ACTION_DROP:
                View view = (View) event.getLocalState();
                listener.onMoveDestination(view, event.getX(), event.getY());
                break;
            case DragEvent.ACTION_DRAG_ENDED:
            default:
                break;
        }

        return true;
    }
}
