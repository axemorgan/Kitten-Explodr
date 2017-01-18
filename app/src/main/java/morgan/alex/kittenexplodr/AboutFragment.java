package morgan.alex.kittenexplodr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class AboutFragment extends DialogFragment {

    public static AboutFragment build() {
        return new AboutFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_fragment, container);
        TextView title = (TextView) view.findViewById(R.id.about_title);
        TextView message = (TextView) view.findViewById(R.id.about_message);

        String titleText = this.getString(R.string.about_title, BuildConfig.VERSION_NAME);
        title.setText(titleText);

        String messageText = this.getString(R.string.about_message);
        message.setText(messageText);

        return view;
    }
}
