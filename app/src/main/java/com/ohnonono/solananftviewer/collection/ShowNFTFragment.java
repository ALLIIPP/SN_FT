package com.ohnonono.solananftviewer.collection;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.ohnonono.solananftviewer.R;

import java.util.Date;


public class ShowNFTFragment extends Fragment {

    private ImageView big_image;
    private ImageView big_image_bgd;
    private FrameLayout container_fl;
    private Animator currentAnimator;
    private ValueAnimator colorAnimStart;
    private ValueAnimator colorAnimEnd;
    private BottomNavigationView navBar;
    private static final int PFP_ANIM_TIME = 500;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shownft, container, false);

        navBar = requireActivity().findViewById(R.id.activity_main_bnv_navbar);
        navBar.setVisibility(View.GONE);

        ShowNFTViewModel viewModel = new ViewModelProvider(requireActivity()).get(ShowNFTViewModel.class);

        ImageView image = view.findViewById(R.id.fragment_img_nftimage);
        TextView name_tv = view.findViewById(R.id.fragment_shownft_tv_name);
        TextView collectionname_tv = view.findViewById(R.id.fragment_shownft_tv_collectionname);
        TextView description_tv = view.findViewById(R.id.fragment_shownft_tv_description);
        TextView currentprice_tv = view.findViewById(R.id.fragment_shownft_tv_price);
        TextView rank_tv = view.findViewById(R.id.fragment_shownft_tv_rank);
        RecyclerView attributes_rv = view.findViewById(R.id.fragment_shownft_rv_attributes);
        TextView created_tv = view.findViewById(R.id.fragment_shownft_tv_created);
        TextView mintaddress = view.findViewById(R.id.fragment_shownft_tv_mintadress);
        TextView viewonsolscan = view.findViewById(R.id.fragment_shownft_tv_viewonsolscan);
        TextView currentpricedollars_tv = view.findViewById(R.id.fragment_shownft_tv_priceindollars);

        big_image = view.findViewById(R.id.fragment_shownft_img_largview);
        big_image_bgd = view.findViewById(R.id.fragment_shownft_img_largview_bgnd);
        container_fl = view.findViewById(R.id.fragment_shownft_fl_container);

        if (viewModel.getMint() != null) {
            name_tv.setText(viewModel.getMint().getName());
            collectionname_tv.setText(viewModel.getMint().getLilinfo().getName());
            description_tv.setText(viewModel.getMint().getLilinfo().getDescription());
            currentprice_tv.setText(requireActivity().getString(R.string.sol_symbol, String.valueOf(viewModel.getMint().getPrice())));
            rank_tv.setText(requireActivity().getString(R.string.fragment_shownft_rank_timesseen, String.valueOf(viewModel.getMint().getRank()), String.valueOf(viewModel.getMint().getRank_explain().get(0).getTotal_seen())));
            currentpricedollars_tv.setText(getString(R.string.parenthized, viewModel.getMint().getPrice_USD()));

            Date time = new Date(viewModel.getMint().getCreated() * 1000);

            created_tv.setText(time.toString());
            mintaddress.setText(viewModel.getMint().getMint());


            viewonsolscan.setText(Html.fromHtml("<a href=\"https://solscan.io/token/" + viewModel.getMint().getMint() + "\">View on Solscan</a>"));
            viewonsolscan.setMovementMethod(LinkMovementMethod.getInstance());
            ShowNFTRecycleViewAdapter adapter = new ShowNFTRecycleViewAdapter(viewModel.getMint().getRank_explain());
            attributes_rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            attributes_rv.setAdapter(adapter);

            Button search_btn = view.findViewById(R.id.fragment_shownft_btn_search);

            Intent intent = new Intent(Intent.ACTION_VIEW);

            switch (viewModel.getMint().getMarket_name()) {
                case "digitaleyes":
                    search_btn.setText(requireActivity().getString(R.string.fragment_shownft_button_text, "Digital Eyes"));
                    search_btn.setBackgroundColor(ResourcesCompat.getColor(requireActivity().getResources(), R.color.digitaleyes, null));
                    intent.setData(Uri.parse("https://digitaleyes.market/item/" + viewModel.getMint().getLilinfo().getName().replaceAll(" ", "%20") + "/" + viewModel.getMint().getMint()));
                    break;
                case "magiceden":
                    search_btn.setText(requireActivity().getString(R.string.fragment_shownft_button_text, "Magic Eden"));
                    search_btn.setBackgroundColor(ResourcesCompat.getColor(requireActivity().getResources(), R.color.magiceden, null));
                    intent.setData(Uri.parse("https://magiceden.io/item-details/" + viewModel.getMint().getMint()));

                    break;
                case "alphaart":
                    search_btn.setText(requireActivity().getString(R.string.fragment_shownft_button_text, "Alpha Art"));
                    search_btn.setBackgroundColor(ResourcesCompat.getColor(requireActivity().getResources(), R.color.alphaart, null));
                    intent.setData(Uri.parse("https://alpha.art/t/" + viewModel.getMint().getMint()));
                    break;
                case "solanart":
                    search_btn.setText(requireActivity().getString(R.string.fragment_shownft_button_text, "Solanart"));
                    search_btn.setBackgroundColor(ResourcesCompat.getColor(requireActivity().getResources(), R.color.solanart, null));
                    intent.setData(Uri.parse("https://solanart.io/collections/" + viewModel.getMint().getLilinfo().getName().replaceAll(" ", "").toLowerCase()));
                    break;

                default:
                    break;
            }
            search_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(intent);
                }
            });
       //     Glide.with(requireActivity().getApplicationContext()).load(viewModel.getMint().getImage()).into(image_background);


            Glide.with(requireActivity().getApplicationContext()).load(viewModel.getMint().getImage()).into(image);

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View vie) {


                    zoomImageFromThumb(image, viewModel.getMint().getImage());

                }
            });
        }


        colorAnimStart = ObjectAnimator.ofInt(big_image_bgd, "backgroundColor", requireContext().getResources().getColor(R.color.translucent, null), requireContext().getResources().getColor(R.color.black, null));
        colorAnimStart.setDuration(PFP_ANIM_TIME);
        colorAnimStart.setEvaluator(new ArgbEvaluator());

        colorAnimEnd = ObjectAnimator.ofInt(big_image_bgd, "backgroundColor", requireContext().getResources().getColor(R.color.black, null), requireContext().getResources().getColor(R.color.translucent, null));
        colorAnimEnd.setDuration(PFP_ANIM_TIME);
        colorAnimEnd.setEvaluator(new ArgbEvaluator());


        ImageView back_button = view.findViewById(R.id.partial_toolbar_backbar_imgv_backbutton);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });


        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (navBar != null) {
            navBar.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (navBar != null) {
            navBar.setVisibility(View.GONE);
        }
    }

    private void zoomImageFromThumb(final View thumbView, String url) {
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        if (currentAnimator != null) {
            currentAnimator.cancel();
        }

        // Load the high-resolution "zoomed-in" image.
        final ImageView expandedImageView = (ImageView) big_image;
        //   expandedImageView.setImageResource(R.drawable.ic_baseline_search_24);
        Glide.with(requireActivity().getApplicationContext()).load(url).into(expandedImageView);


        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        container_fl
                .getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);
        big_image_bgd.setVisibility(View.VISIBLE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
                        startScale, 1f))
                .with(ObjectAnimator.ofFloat(expandedImageView,
                        View.SCALE_Y, startScale, 1f));
        set.setDuration(PFP_ANIM_TIME);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                currentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                currentAnimator = null;
            }
        });
        set.start();
        colorAnimStart.start();
        currentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentAnimator != null) {
                    currentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel,
                // back to their original values.
                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator
                        .ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.Y, startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_Y, startScaleFinal));
                set.setDuration(PFP_ANIM_TIME);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        big_image_bgd.setVisibility(View.GONE);
                        currentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        big_image_bgd.setVisibility(View.GONE);
                        expandedImageView.setVisibility(View.GONE);
                        currentAnimator = null;
                    }
                });
                colorAnimEnd.start();
                set.start();
                currentAnimator = set;
            }
        });
    }


}

