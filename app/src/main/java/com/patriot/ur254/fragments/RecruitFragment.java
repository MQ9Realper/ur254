package com.patriot.ur254.fragments;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.patriot.ur254.R;
import com.patriot.ur254.database.D2;
import com.patriot.ur254.utils.G4;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import models.Contact;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecruitFragment extends Fragment {
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private ListView list;
    private ArrayList arraylist;
    private ArrayList<Contact> arraylist2;
    private int contactcount = 0;
    private String contactname, contactnumber, dbnumber, countrycode, number;
    private ArrayList<String> listContacName = new ArrayList<String>();
    private ArrayList<String> listContactNumber = new ArrayList<String>();
    private TymeContactListViewAdapter adapter2;


    public RecruitFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recruit, container, false);
        list = (ListView) view.findViewById(R.id.listViewContacts);
        LoadContacts();
        return view;
    }

    private void LoadContacts() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_CONTACTS)) {
                    AlertDialog alertReadContacts = new AlertDialog.Builder(getActivity()).create();
                    alertReadContacts.setTitle("Read Contact Permission");
                    alertReadContacts.setMessage("UR254 requires access to your contacts.");
                    alertReadContacts.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
                        }
                    });
                    alertReadContacts.show();

                } else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
                }
            } else {
                initializeContactsFetching();
            }

        } else {
            initializeContactsFetching();
        }
    }

    private void initializeContactsFetching() {
        getAllContacts();
        refreshList();
    }

    private void getAllContacts() {
        arraylist2 = new ArrayList<Contact>();
        arraylist2.clear();
        D2 info = new D2(getActivity());
        Cursor cursor = info.getAllContacts();

        String dbname = "dbname";
        String dbno = "dbno";
        String phno = "phno";
        String contactimage = "";

        if (cursor.getCount() > 0) {
            //toast("yes contacts yes found", display);
            contactcount = cursor.getCount();
            if (cursor.moveToFirst()) {
                while (cursor.moveToNext()) {
                    contactname = cursor.getString(1);
                    dbnumber = cursor.getString(2);
                    contactimage = cursor.getString(4);
                    contactnumber = dbnumber;
                    countrycode = contactnumber.substring(0, 3);
                    if (countrycode.equals("254")) {
                        contactnumber = contactnumber.substring(3);
                        contactnumber = "0" + contactnumber;
                    }
                    setContactList(contactimage, contactname, contactnumber);
                }

            }

        }
    }

    private void setContactList(String thecontactimage, String thecontactname, String thecontactnumber) {
        String image = thecontactimage;
        String name = thecontactname;
        String thenumber = thecontactnumber;
        countrycode = thenumber.substring(0, 3);
        if (countrycode.equals("254")) {
            thenumber = thenumber.substring(3);
            thenumber = "0" + thenumber;
        }
        String subscriber = getSubscriber(thenumber);
        String comment = thecontactnumber;
        Contact participant = new Contact(image, name, thenumber, subscriber, comment, false);
        arraylist2.add(participant);
    }

    private String getSubscriber(String number) {
        String subscriber = "";
        if (number.startsWith("070") || number.startsWith("071") || number.startsWith("072")) {
            subscriber = "Safaricom";
        } else if (number.startsWith("073") || number.startsWith("078") || number.startsWith("075")) {
            subscriber = "Airtel";
        } else if (number.startsWith("077")) {
            subscriber = "Orange";
        } else if (number.startsWith("076")) {
            subscriber = "Equitel";
        } else {
            subscriber = "Unknown";
        }
        return subscriber;
    }

    private void refreshList() {
        instantiateEverything();
        adapter2 = new TymeContactListViewAdapter(getActivity(), arraylist2);
        list.setAdapter(adapter2);
        list.invalidate();
    }

    private void instantiateEverything() {
        listContacName = new ArrayList<String>();
        listContactNumber = new ArrayList<String>();
    }

    private class TymeContactListViewAdapter extends BaseAdapter {
        Context mContext;
        LayoutInflater inflater;
        private List<Contact> contactList = null;
        private ArrayList<Contact> arraylist2;

        private TymeContactListViewAdapter(Context context, List<Contact> contactList) {
            mContext = context;
            this.contactList = contactList;
            inflater = LayoutInflater.from(mContext);
            this.arraylist2 = new ArrayList<Contact>();
            this.arraylist2.addAll(contactList);
        }

        private class ViewHolder {
            CircleImageView userImage;
            TextView userName, userNumber, userComment, txtCommentTime;
            RelativeLayout commentitem_profilelayout;
            CheckBox chkbox;
        }

        @Override
        public int getCount() {
            return contactList.size();
        }

        public void clearthis() {
            arraylist2.clear();

        }

        public void filter(String charText) {
            charText = charText.toLowerCase(Locale.getDefault());
            contactList.clear();
            if (charText.length() == 0) {
                contactList.addAll(arraylist2);
            } else {
                for (Contact wp : arraylist2) {
                    if (wp.getUserName().toLowerCase(Locale.getDefault()).contains(charText)) {
                        contactList.add(wp);
                    }
                }
            }
            notifyDataSetChanged();
        }

        public void chooseSelected() {
            contactList.clear();
            for (Contact wp : arraylist2) {
                if (wp.isSelected()) {
                    contactList.add(wp);
                }
            }
            notifyDataSetChanged();
        }

        public void chooseAll() {
            contactList.clear();
            for (Contact wp : arraylist2) {
                wp.setSelected(false);
                contactList.add(wp);
            }
            notifyDataSetChanged();
        }

        @Override
        public Contact getItem(int position) {
            return contactList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View view, ViewGroup parent) {
            final TymeContactListViewAdapter.ViewHolder holder;
            if (view == null) {
                holder = new TymeContactListViewAdapter.ViewHolder();
                view = inflater.inflate(R.layout.layout_contacts_list_item, parent, false);
                holder.commentitem_profilelayout = (RelativeLayout) view.findViewById(R.id.commentitem_profilelayout);
                holder.userImage = (CircleImageView) view.findViewById(R.id.txtFriendCommentImage);
                holder.userName = (TextView) view.findViewById(R.id.txtFriendCommentName);
                holder.txtCommentTime = (TextView) view.findViewById(R.id.txtCommentTime);
                holder.userComment = (TextView) view.findViewById(R.id.txtFriendComment);
                holder.userNumber = (TextView) view.findViewById(R.id.userNumber);
                holder.chkbox = (CheckBox) view.findViewById(R.id.chkbox);
                view.setTag(holder);

            } else {
                holder = (TymeContactListViewAdapter.ViewHolder) view.getTag();
            }

            final String name = contactList.get(position).getUserName();
            final String number = contactList.get(position).getUserNumber();
            holder.userName.setText(name);
            holder.userNumber.setText(number);
            holder.userComment.setText(contactList.get(position).getUserComment());
            holder.txtCommentTime.setText(contactList.get(position).getUserDate());

            Contact adapterposition = arraylist2.get(position);
            holder.userImage.setTag(adapterposition);
            holder.userName.setTag(adapterposition);
            holder.userComment.setTag(adapterposition);
            holder.userNumber.setTag(adapterposition);

            String base64image = contactList.get(position).getUserImage();

            View.OnClickListener menuselect = new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (contactList.get(position).isSelected()) {
                        contactList.get(position).setSelected(false);
                        listContacName.remove(name);
                        listContactNumber.remove(number);
                        holder.chkbox.setChecked(false);
                    } else {
                        contactList.get(position).setSelected(true);
                        listContacName.add(name);
                        listContactNumber.add(number);
                        holder.chkbox.setChecked(true);
                    }


                }
            };
            holder.commentitem_profilelayout.setOnClickListener(menuselect);

            holder.chkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                         @Override
                                                         public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                                             if (!isChecked) {
                                                                 contactList.get(position).setSelected(false);
                                                                 listContacName.remove(name);
                                                                 listContactNumber.remove(number);
                                                                 holder.chkbox.setChecked(false);

                                                             } else {
                                                                 contactList.get(position).setSelected(true);
                                                                 listContacName.add(name);
                                                                 listContactNumber.add(number);
                                                                 holder.chkbox.setChecked(true);
                                                             }

                                                         }
                                                     }
            );

            if (contactList.get(position).isSelected()) {
                holder.chkbox.setChecked(true);
            } else {
                if ((position % 2) == 0) {
                    holder.commentitem_profilelayout.setBackgroundColor(Color.parseColor("#ffffff"));
                } else {
                    holder.commentitem_profilelayout.setBackgroundColor(Color.parseColor("#30de182f"));
                }
                holder.chkbox.setChecked(false);
            }


            try {
                if (base64image.equals("")) {
                    base64image = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (base64image != null) {

                loadBitmap(base64image, holder.userImage);

            } else {
                holder.userImage.setImageResource(R.drawable.profile_photo);
            }
            return view;
        }

    }

    public void loadBitmap(String resStr, ImageView imageView) {
        if (cancelPotentialWork(resStr, imageView)) {
            Bitmap bmperson = null;
            final BitmapWorkerTask task = new BitmapWorkerTask(imageView);
            final AsyncDrawable asyncDrawable = new AsyncDrawable(getResources(), bmperson, task);
            imageView.setImageDrawable(asyncDrawable);
            task.execute(resStr);
        }
    }

    private static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }

    private class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;
        private String data = "";

        private BitmapWorkerTask(ImageView imageView) {
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            data = params[0];
            return decodeSampledBitmapFromResource(getResources(), data, 100, 100);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                bitmap = null;
            }

            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);
                if (this == bitmapWorkerTask && imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, String base64image, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        byte[] url = G4.decode(base64image, G4.DEFAULT);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(url, 0, url.length, options);
    }

    static class AsyncDrawable extends BitmapDrawable {
        private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;

        private AsyncDrawable(Resources res, Bitmap bitmap, BitmapWorkerTask bitmapWorkerTask) {
            super(res, bitmap);
            bitmapWorkerTaskReference = new WeakReference<BitmapWorkerTask>(bitmapWorkerTask);
        }

        public BitmapWorkerTask getBitmapWorkerTask() {
            return bitmapWorkerTaskReference.get();
        }
    }

    public static boolean cancelPotentialWork(String data, ImageView imageView) {
        final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);
        if (bitmapWorkerTask != null) {
            final String bitmapData = bitmapWorkerTask.data;
            if (bitmapData.equals("") || !bitmapData.equals(data)) {
                bitmapWorkerTask.cancel(true);
            } else {
                return false;
            }
        }
        return true;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

}
