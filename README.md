# DrillEdu — School Safety Drill Application (v2.0)

**DrillEdu** is a modern, offline-capable Android application built with **Java** and **XML ViewBinding** in Android Studio for school safety drill education, disaster preparedness, interactive hazard reporting, emergency evacuation maps, achievements, and knowledge practice.

---

## 🌟 Key Features

1. **Original DrillEdu Branding**: Custom vector drawable logo (School building inside a protective shield emblem), splash screen, launcher icons, and top app bar identity.
2. **Glassmorphism Theme**: Dark Navy (`#061A2F`), Secondary Navy (`#0B2545`), Primary Safety Blue (`#168BFF`), Emergency Yellow (`#FFC928`), Success Green (`#22C55E`), Emergency Red (`#FF4757`).
3. **Home Dashboard**: Welcome Banner, Daily Safety Streak Badge ("🔥 3-Day Streak"), 4 Core Module Cards, Interactive Safety Tools Carousel, Safety Tip of the Day, Overall Preparedness Progress Indicator, Quick Emergency Actions.
4. **Safety Guidelines**: Comprehensive rules and procedures for Fire, Earthquake, Flood, Severe Weather, Medical Emergencies, Electrical Safety, Evacuation, and General Safety with Before, During, and After steps.
5. **Emergency Drills**: Step-by-step procedures, preparation instructions, Do's & Don'ts, safety reminders, interactive "Mark as Completed" tracking.
6. **Preparedness Checklist**: Interactive checkboxes categorized by BEFORE DRILL, DURING DRILL, and AFTER DRILL with percentage progress tracking and reset confirmation.
7. **Learning Resources**: Searchable library of articles, infographics, first-aid guides, reading time badges, full offline reader, and bookmarking.
8. **Emergency Contacts**: Verified public emergency numbers (112 National, 101 Fire, 102 Ambulance, 100 Police), school helpline, and safety coordinator contacts with dial intent confirmation dialogs and editable family/school numbers.
9. **Quiz & Practice**: Multiple-choice quizzes with immediate explanation feedback, scoring, result breakdown, and retry option.
10. **⏱️ Real-Time Drill Simulation Timer (`DrillTimerActivity`)**: Timed evacuation challenge (2-minute Fire Evacuation, 60-second Earthquake Drop-Cover-Hold) with phase prompts and efficiency scoring.
11. **⚠️ Hazard Incident Reporter (`ReportIncidentActivity`)**: Campus hazard logger with urgency selector chips (Low, Medium, High, Critical), location tagger, and local SQLite tracking.
12. **🏆 Badges & Achievements (`AchievementsActivity`)**: Unlockable badges (*First Responder*, *Fire Warden*, *Earthquake Master*, *Checklist Champion*, *Safety Reporter*, *Speedy Evacuator*) with live progress counters.
13. **🗺️ Campus Evacuation Map (`EvacuationMapActivity`)**: Visual floorplan vector diagram illustrating primary exit corridors and outdoor safe assembly zones across all building levels.
14. **🃏 Safety Flashcards (`FlashcardsActivity`)**: Interactive flip-cards for rapid emergency memory practice (PASS Extinguisher technique, Smoke Crawling, Drop-Cover-Hold).
15. **My Progress & Settings**: Local statistics (drills completed, checklist %, quiz average score, bookmarked items), student display name customization, and notification preferences.

---

## 🛠️ Architecture & Tech Stack

- **Language**: Java 11
- **UI Framework**: XML Layouts with Material Design 3 Components (`com.google.android.material`) & ViewBinding
- **Local Database**: Custom `SQLiteOpenHelper` (`DrillEduDbHelper.java` - Version 2) prepopulated with comprehensive authoritative offline safety content
- **Preferences**: `SharedPreferences` via `PreferencesManager.java`
- **Minimum SDK**: API 24 (Android 7.0 Nougat)
- **Target SDK**: API 35

---

## 🚀 Setup & Execution Instructions

1. Open **Android Studio** (2024.1 or higher).
2. Select **File > Open** and navigate to the `DrillEdu` root project directory (`D:/DrillEdu`).
3. Allow Gradle to synchronize dependencies automatically.
4. Connect an Android device or launch an Android Virtual Device (AVD) running API 24 or higher.
5. Click **Run > Run 'app'** or press `Shift + F10` to build and deploy the debug APK.

---

## 📄 License & Disclaimer

This project is licensed under the [Apache License, Version 2.0](LICENSE).

DrillEdu is an educational resource developed for disaster management and school safety drill awareness. Always follow official instructions from school staff and emergency responders during real emergency situations.
