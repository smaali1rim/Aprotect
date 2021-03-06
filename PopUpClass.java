package com.example.myapplication;
import android.view.*;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class PopUpClass extends AppsActivity{

        //PopupWindow display method

        public void showPopupWindow(final View view, int r) {
            System.out.println();
            int v ;
            if (r==0)
              {v=R.layout.pop_up_layout2;}
              else {
                  v = R.layout.pop_up_layout;
              }
            //Create a View object yourself through inflater
            LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
            View popupView = inflater.inflate(v, null);

            //Specify the length and width through constants
            int width = LinearLayout.LayoutParams.MATCH_PARENT;
            int height = LinearLayout.LayoutParams.MATCH_PARENT;

            //Make Inactive Items Outside Of PopupWindow
            boolean focusable = true;

            //Create a window with our parameters
            final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

            //Set the location of the window on the screen
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

            //Handler for clicking on the inactive zone of the window

            popupView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    //Close the window when clicked
                    popupWindow.dismiss();
                    return true;
                }
            });
        }


}
