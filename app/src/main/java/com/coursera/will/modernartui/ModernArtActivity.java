package com.coursera.will.modernartui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.preference.DialogPreference;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;


public class ModernArtActivity extends ActionBarActivity implements SeekBar.OnSeekBarChangeListener {

    private static final int MORE_INFO = 0;
    private static final String CHOOSER_TEXT = "Open www.moma.org using:";

    private final int defaultColors[] =
            { 0xeeee00, 0x008888, 0x0088cc, 0x880044,
              0x8800cc, 0x0000cc, 0xddffdd, 0x00cc00 };

    private LinearLayout[] rectangles = new LinearLayout[8];
    private DialogFragment mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modern_art);

        SeekBar seekBar = ( SeekBar ) findViewById( R.id.seekBar );
        seekBar.setOnSeekBarChangeListener( this );

        rectangles[0] = ( LinearLayout ) findViewById( R.id.rect0 );
        rectangles[1] = ( LinearLayout ) findViewById( R.id.rect1 );
        rectangles[2] = ( LinearLayout ) findViewById( R.id.rect2 );
        rectangles[3] = ( LinearLayout ) findViewById( R.id.rect3 );
        rectangles[4] = ( LinearLayout ) findViewById( R.id.rect4 );
        rectangles[5] = ( LinearLayout ) findViewById( R.id.rect5 );
        rectangles[6] = ( LinearLayout ) findViewById( R.id.rect6 );
        rectangles[7] = ( LinearLayout ) findViewById( R.id.rect7 );

        setColors( defaultColors );
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int newColors[] = new int[8];

        for( int i = 0; i < 8; i++ ) {
            newColors[i] =
                    (defaultColors[i] + (0xff0001 * progress) + (0xff01 * progress) + progress);
        }

        setColors( newColors );
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void setColors(int[] colors) {

        for(int i = 0; i < 8; i++ ) {
            rectangles[i].setBackgroundColor(0xff000000 | colors[i]);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_modern_art, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.moreInfo) {
            mDialog = MoreInfoDialogFragment.newInstance();
            mDialog.show( getFragmentManager(), "moreInfo" );
        }

        return super.onOptionsItemSelected(item);
    }

    public static class MoreInfoDialogFragment extends DialogFragment {

        public static MoreInfoDialogFragment newInstance() { return new MoreInfoDialogFragment(); }

        public Dialog onCreateDialog( Bundle savedInstanceState ) {
            AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
            builder.setTitle( R.string.dialogTitle )
                    .setMessage( R.string.dialogMsg )
                    .setPositiveButton( R.string.visitMOMA, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent();
                            i.setAction( Intent.ACTION_VIEW );
                            i.setData( Uri.parse("http://www.moma.org") );

                            Intent chooserIntent = Intent.createChooser( i, CHOOSER_TEXT );
                            startActivity( chooserIntent );
                        }
                    })
                    .setNeutralButton(R.string.notNow, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

            return builder.create();
        }
    }
}


