package com.example.drilledu.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.drilledu.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class FlashcardsActivity extends AppCompatActivity {

    private static class CardModel {
        String front;
        String back;
        int iconRes;

        CardModel(String front, String back, int iconRes) {
            this.front = front;
            this.back = back;
            this.iconRes = iconRes;
        }
    }

    private final List<CardModel> deck = new ArrayList<>();
    private int currentIndex = 0;
    private boolean isShowingFront = true;

    private TextView tvCounter;
    private TextView tvSideTag;
    private TextView tvContent;
    private ImageView ivIcon;
    private MaterialCardView cardContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcards);

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());

        tvCounter = findViewById(R.id.tv_flashcard_counter);
        tvSideTag = findViewById(R.id.tv_flashcard_side_tag);
        tvContent = findViewById(R.id.tv_flashcard_content);
        ivIcon = findViewById(R.id.iv_flashcard_icon);
        cardContainer = findViewById(R.id.card_flip_container);

        initDeck();
        displayCard();

        cardContainer.setOnClickListener(v -> flipCard());
        findViewById(R.id.btn_next_flashcard).setOnClickListener(v -> {
            currentIndex = (currentIndex + 1) % deck.size();
            isShowingFront = true;
            displayCard();
        });
    }

    private void initDeck() {
        deck.add(new CardModel(
                "What is the P.A.S.S. technique for using a Fire Extinguisher?",
                "P - Pull safety pin\nA - Aim nozzle at base of fire\nS - Squeeze lever slowly\nS - Sweep nozzle side to side",
                R.drawable.ic_fire
        ));
        deck.add(new CardModel(
                "What is the 3-step action for Earthquake safety indoors?",
                "DROP onto hands & knees\nCOVER head and neck under desk\nHOLD ON to shelter until shaking stops",
                R.drawable.ic_earthquake
        ));
        deck.add(new CardModel(
                "Why should you crawl low when escaping a smoky room?",
                "Toxic smoke and hot gases rise to ceiling.\nThe clearest, coolest air is within 12 inches of the floor.",
                R.drawable.ic_fire
        ));
        deck.add(new CardModel(
                "What is the single unified emergency helpline number in India?",
                "112 — Connects directly to Police (100), Fire (101), and Ambulance (102).",
                R.drawable.ic_call
        ));
        deck.add(new CardModel(
                "What should students NEVER do during a school lockdown?",
                "1. NEVER open doors for anyone during lockdown.\n2. NEVER make noise or shine phone lights.\nWait for police or principal clearance.",
                R.drawable.ic_lockdown
        ));
    }

    private void displayCard() {
        if (deck.isEmpty()) return;
        CardModel card = deck.get(currentIndex);
        tvCounter.setText("Card " + (currentIndex + 1) + " of " + deck.size());

        ivIcon.setImageResource(card.iconRes);

        if (isShowingFront) {
            tvSideTag.setText("[ QUESTION - TAP TO REVEAL ]");
            tvSideTag.setTextColor(getColor(R.color.safety_yellow));
            tvContent.setText(card.front);
        } else {
            tvSideTag.setText("[ ANSWER / EXPLANATION ]");
            tvSideTag.setTextColor(getColor(R.color.success_green));
            tvContent.setText(card.back);
        }
    }

    private void flipCard() {
        cardContainer.animate().rotationY(90).setDuration(150).withEndAction(() -> {
            isShowingFront = !isShowingFront;
            displayCard();
            cardContainer.setRotationY(-90);
            cardContainer.animate().rotationY(0).setDuration(150).start();
        }).start();
    }
}