package com.example.drilledu.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.drilledu.models.ChecklistItem;
import com.example.drilledu.models.DrillItem;
import com.example.drilledu.models.EmergencyContact;
import com.example.drilledu.models.LearningResource;
import com.example.drilledu.models.QuizQuestion;
import com.example.drilledu.models.QuizResult;
import com.example.drilledu.models.SafetyGuide;

import java.util.ArrayList;
import java.util.List;

public class DrillEduDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "drilledu.db";
    private static final int DATABASE_VERSION = 1;

    // Table names
    private static final String TABLE_SAFETY_GUIDES = "safety_guides";
    private static final String TABLE_DRILLS = "drills";
    private static final String TABLE_CHECKLISTS = "checklists";
    private static final String TABLE_RESOURCES = "resources";
    private static final String TABLE_QUIZZES = "quizzes";
    private static final String TABLE_QUIZ_RESULTS = "quiz_results";
    private static final String TABLE_CONTACTS = "emergency_contacts";

    private static DrillEduDbHelper instance;

    public static synchronized DrillEduDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DrillEduDbHelper(context.getApplicationContext());
        }
        return instance;
    }

    public DrillEduDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Safety Guides table
        db.execSQL("CREATE TABLE " + TABLE_SAFETY_GUIDES + " (" +
                "id TEXT PRIMARY KEY, " +
                "title TEXT, category TEXT, summary TEXT, iconName TEXT, " +
                "intro TEXT, hazards TEXT, prevention TEXT, " +
                "beforeSteps TEXT, duringSteps TEXT, afterSteps TEXT, warnings TEXT)");

        // Create Drills table
        db.execSQL("CREATE TABLE " + TABLE_DRILLS + " (" +
                "id TEXT PRIMARY KEY, title TEXT, type TEXT, iconName TEXT, " +
                "purpose TEXT, prepInstructions TEXT, procedureSteps TEXT, " +
                "dosAndDonts TEXT, safetyReminders TEXT, isCompleted INTEGER, completedTimestamp INTEGER)");

        // Create Checklists table
        db.execSQL("CREATE TABLE " + TABLE_CHECKLISTS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, category TEXT, text TEXT, " +
                "drillType TEXT, isCompleted INTEGER)");

        // Create Resources table
        db.execSQL("CREATE TABLE " + TABLE_RESOURCES + " (" +
                "id TEXT PRIMARY KEY, title TEXT, category TEXT, type TEXT, " +
                "readTime TEXT, summary TEXT, fullContent TEXT, iconName TEXT, isBookmarked INTEGER)");

        // Create Quizzes table
        db.execSQL("CREATE TABLE " + TABLE_QUIZZES + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, category TEXT, questionText TEXT, " +
                "optionA TEXT, optionB TEXT, optionC TEXT, optionD TEXT, " +
                "correctOptionIndex INTEGER, explanation TEXT)");

        // Create Quiz Results table
        db.execSQL("CREATE TABLE " + TABLE_QUIZ_RESULTS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, category TEXT, score INTEGER, " +
                "totalQuestions INTEGER, timestamp INTEGER)");

        // Create Emergency Contacts table
        db.execSQL("CREATE TABLE " + TABLE_CONTACTS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, category TEXT, " +
                "phoneNumber TEXT, description TEXT, iconName TEXT, isCustom INTEGER)");

        seedData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAFETY_GUIDES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DRILLS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHECKLISTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESOURCES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUIZZES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUIZ_RESULTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }

    private void seedData(SQLiteDatabase db) {
        // Seed Safety Guides
        insertGuide(db, "guide_fire", "Fire Safety", "Fire", "Prevention & action steps during school fires", "ic_fire",
                "School fires can spread rapidly. Staying calm and taking immediate evacuation action is vital.",
                "Electrical short circuits, combustible laboratory chemicals, damaged wiring, unattended heat sources.",
                "Keep emergency exits clear. Inspect school electrical systems regularly. Maintain functional fire extinguishers.",
                "1. Know all primary and secondary evacuation routes.\n2. Locate fire alarm pull stations and fire extinguishers.\n3. Participate actively in scheduled fire drills.",
                "1. Remain calm and follow teacher instructions.\n2. Leave belongings behind and evacuate immediately.\n3. Stay low under smoke to avoid toxic gas inhalation.\n4. Feel door handles before opening; do not open if hot.",
                "1. Proceed directly to designated assembly point.\n2. Remain with class for attendance roll call.\n3. Never re-enter the school building until given official clearance.",
                "WARNING: Never use elevators during a fire evacuation. Do not hide in closets or bathrooms.");

        insertGuide(db, "guide_earthquake", "Earthquake Safety", "Earthquake", "Drop, Cover, and Hold On emergency response", "ic_earthquake",
                "Earthquakes strike without warning. Proper protective response saves lives during shaking.",
                "Falling glass, collapsing ceilings, unanchored bookcases, electrical hazards.",
                "Anchor heavy furniture and shelves. Secure overhead light fixtures. Identify safe cover zones in classrooms.",
                "1. Identify safe spots under sturdy desks or interior walls.\n2. Keep hallways and exit paths clear of obstructions.\n3. Learn the Drop, Cover, and Hold On technique.",
                "1. DROP onto your hands and knees immediately.\n2. COVER your head and neck under a sturdy desk or table.\n3. HOLD ON to your shelter until shaking stops completely.\n4. If outdoors, move to an open area away from buildings and trees.",
                "1. Evacuate safely when shaking stops.\n2. Watch for fallen power lines and debris.\n3. Report injuries to teachers or response staff.",
                "WARNING: Do not run outside while shaking is active. Falling debris near building exteriors poses extreme risk.");

        insertGuide(db, "guide_flood", "Flood Safety", "Flood", "Stay informed, reach higher ground, and avoid floodwaters", "ic_flood",
                "Monsoon floods and heavy rains can submerge school grounds and impair transportation.",
                "Swift-moving water, contaminated floodwaters, electrical shock hazards.",
                "Monitor local weather bulletins. Ensure school drainage channels are clear.",
                "1. Understand school emergency dismissal procedures.\n2. Keep emergency contact details updated.\n3. Know elevated safe zones in the school.",
                "1. Move immediately to upper floors if instructed.\n2. Avoid wading or walking through flowing floodwater.\n3. Stay away from electrical panels and submerged wiring.",
                "1. Wait for safe transport clearance.\n2. Do not consume water from flooded taps.\n3. Inform parents and staff when safely evacuated.",
                "WARNING: Moving floodwater as shallow as 6 inches can knock you down. Never play in floodwaters.");

        insertGuide(db, "guide_weather", "Severe Weather Safety", "Weather", "Protection against thunderstorms, high winds, and cyclones", "ic_weather",
                "Severe storms, lightning, and extreme winds pose serious risks to students.",
                "Lightning strikes, flying debris, falling tree branches, shattered windows.",
                "Maintain school weather warning systems. Conduct routine window shutter inspections.",
                "1. Charge emergency communication devices.\n2. Identify interior windowless safe rooms.",
                "1. Move away from glass windows and exterior doors.\n2. Crouch low with hands covering neck if lightning is nearby.\n3. Remain inside sturdy structures until warnings expire.",
                "1. Inspect surroundings for fallen power lines.\n2. Report structural damage immediately.",
                "WARNING: Never shelter under isolated trees during lightning storms.");

        insertGuide(db, "guide_medical", "Medical Emergencies", "Medical", "First aid basics, CPR awareness, and emergency response", "ic_medical",
                "Prompt first-aid response stabilizes injuries until professional medical assistance arrives.",
                "Severe bleeding, choking, fractures, allergic reactions, heat exhaustion.",
                "Maintain fully stocked first-aid kits in all school blocks.",
                "1. Memorize school medical room location.\n2. Know emergency contact numbers (102/112).",
                "1. Stay calm and call a teacher or school nurse.\n2. Do not move injured persons unless immediate danger exists.\n3. Apply pressure to severe bleeding using clean cloth.",
                "1. Hand over to ambulance responders.\n2. Notify parents promptly.",
                "WARNING: Do not administer medications without qualified medical authority.");

        insertGuide(db, "guide_general", "General Safety Tips", "General", "Daily hazard prevention and school safety awareness", "ic_shield",
                "Daily vigilance prevents accidents and fosters a secure learning environment.",
                "Slips, trips, lab accidents, stairwell crowding, unauthorized visitors.",
                "Report broken equipment, unlighted stairwells, or suspicious activity.",
                "1. Know emergency exit locations.\n2. Wear appropriate protective gear in science labs.",
                "1. Walk safely without rushing in corridors.\n2. Follow school safety signs and rules.",
                "1. Report hazards to school administration.",
                "WARNING: Safety is everyone's responsibility. Never ignore safety rules.");

        // Seed Drills
        insertDrill(db, "drill_fire", "Fire Evacuation Drill", "Fire", "ic_fire",
                "Practice rapid, orderly evacuation during a simulated fire emergency.",
                "Study classroom evacuation maps. Identify primary and secondary exits.",
                "1. Alarm sounds: Stop all activity immediately.\n2. Line up silently at classroom door.\n3. Follow teacher along assigned exit route.\n4. Walk briskly; do not run or push.\n5. Assemble at designated sports field location.\n6. Answer roll call.",
                "DO: Leave all bags behind. Stay in single file.\nDONT: Do not panic. Do not return for personal items.",
                "Remember: Crawl low under smoke!", 0, 0);

        insertDrill(db, "drill_earthquake", "Earthquake Preparedness Drill", "Earthquake", "ic_earthquake",
                "Practice immediate self-protection when earthquake shaking occurs.",
                "Identify sturdy desks and interior support walls in classroom.",
                "1. Drill signal given ('Earthquake! Drop, Cover, Hold!').\n2. Immediately DROP to hands and knees.\n3. Take COVER under desk or table.\n4. HOLD ON to table leg for 60 seconds.\n5. When clear signal sounds, evacuate to open ground.",
                "DO: Protect head and neck. Hold shelter firmly.\nDONT: Do not stand near windows or tall cabinets.",
                "If outdoors during shaking, drop in open ground away from poles.", 0, 0);

        insertDrill(db, "drill_lockdown", "School Lockdown Drill", "Lockdown", "ic_lockdown",
                "Learn shelter-in-place procedures for active external threats.",
                "Understand lockdown signal and silent assembly procedures.",
                "1. Announcement 'Lockdown! Lockdown!'.\n2. Clear hallways; gather into nearest classroom.\n3. Lock classroom doors and close window blinds.\n4. Turn off lights and silence mobile devices.\n5. Sit silently away from doors and windows.\n6. Await official administrator clearance.",
                "DO: Maintain total silence. Stay hidden.\nDONT: Do not open doors for anyone during lockdown.",
                "Silence is critical during lockdown drills.", 0, 0);

        insertDrill(db, "drill_flood", "Flood & High Water Drill", "Flood", "ic_flood",
                "Practice orderly evacuation to upper floors during flash flooding.",
                "Learn vertical evacuation routes to 2nd/3rd floor assembly areas.",
                "1. Flood warning issued by school coordinator.\n2. Turn off electrical switches before leaving.\n3. Move calmly up stairwells to 2nd floor auditorium.\n4. Conduct student roll call and verify headcount.",
                "DO: Move upward. Keep electronics dry.\nDONT: Do not enter flooded ground-level rooms.",
                "Water moves fast; evacuate early.", 0, 0);

        insertDrill(db, "drill_weather", "Severe Storm Drill", "Weather", "ic_weather",
                "Practice taking shelter during severe gales and thunderstorms.",
                "Locate windowless interior corridors and storm shelters.",
                "1. Severe storm alarm sounds.\n2. Move to central ground-floor hallway.\n3. Sit against interior wall away from windows.\n4. Cover head with hands or backpack.",
                "DO: Protect head. Stay away from glass.\nDONT: Do not stay in prefabricated or temporary structures.",
                "Remain sheltered until all-clear is announced.", 0, 0);

        insertDrill(db, "drill_evacuation", "General Evacuation Drill", "Evacuation", "ic_evacuation",
                "Master rapid school-wide assembly and headcount procedures.",
                "Review exit routes for all campus facilities.",
                "1. General alarm sounds.\n2. Proceed in orderly double lines along main exit path.\n3. Report to central assembly ground.\n4. Class leaders assist teacher with attendance list.",
                "DO: Assist classmates needing help. Remain quiet.\nDONT: Do not block exit gates.",
                "Every second counts during an evacuation.", 0, 0);

        // Seed Checklist Items
        insertChecklist(db, "BEFORE", "Know the designated emergency exits for your classroom block", "General", 0);
        insertChecklist(db, "BEFORE", "Learn the school evacuation route and secondary backup exit", "Fire", 0);
        insertChecklist(db, "BEFORE", "Identify designated safe assembly point on school grounds", "General", 0);
        insertChecklist(db, "BEFORE", "Locate classroom first-aid kit and emergency fire pull station", "Fire", 0);
        insertChecklist(db, "BEFORE", "Identify sturdy desks suitable for Drop, Cover, and Hold On", "Earthquake", 0);

        insertChecklist(db, "DURING", "Stay calm and listen attentively to teacher instructions", "General", 0);
        insertChecklist(db, "DURING", "Leave personal heavy items behind and evacuate immediately", "Fire", 0);
        insertChecklist(db, "DURING", "Walk briskly without running, pushing, or tripping classmates", "Evacuation", 0);
        insertChecklist(db, "DURING", "Crawl low beneath smoke if evacuating through smoky areas", "Fire", 0);
        insertChecklist(db, "DURING", "Drop, Cover, and Hold On firmly under a desk during shaking", "Earthquake", 0);

        insertChecklist(db, "AFTER", "Report directly to assigned class assembly area", "General", 0);
        insertChecklist(db, "AFTER", "Answer attendance roll call immediately when name is called", "General", 0);
        insertChecklist(db, "AFTER", "Report any injured classmates or hazards to teacher", "Medical", 0);
        insertChecklist(db, "AFTER", "Remain with class until official clear signal is given", "General", 0);

        // Seed Resources
        insertResource(db, "res_1", "School Fire Safety Guide for Students", "Articles", "Article", "3 min read",
                "Essential rules on fire prevention, Smoke safety, and exit maneuvers.",
                "Fire safety in school starts with prevention and readiness. Students should know all primary emergency exits in their building. If you spot smoke or flame, alert a teacher or activate the nearest manual call point. When the alarm sounds, exit silently in single file. If smoke is present, stay low to the floor where air is cleaner.", "ic_fire", 0);

        insertResource(db, "res_2", "Earthquake Preparedness Infographic", "Infographics", "Infographic", "2 min read",
                "Visual guide to Drop, Cover, and Hold On in classrooms.",
                "DROP to your knees to avoid being knocked over. COVER your head and neck under a sturdy desk or table. HOLD ON to your shelter until shaking stops. If no desk is nearby, cover head with arms against an interior wall.", "ic_earthquake", 0);

        insertResource(db, "res_3", "First Aid Essentials for Schools", "First-Aid", "Guide", "4 min read",
                "Basic response for bleeding, burns, sprains, and fainting.",
                "First aid knowledge is crucial during school emergencies. For minor burns, flush with cool running water for 10 minutes. For severe bleeding, apply firm pressure with a clean cloth. Never move a person suspected of spinal or head injury unless in immediate danger.", "ic_medical", 0);

        insertResource(db, "res_4", "Monsoon & Flood Safety Tips", "Tips", "Guide", "3 min read",
                "Precautionary guidelines during heavy rains and waterlogging.",
                "Monsoons bring flood risks to low-lying school campuses. Avoid walking through stagnant water as hidden open drains pose extreme risk. Keep electrical appliances unplugged during storms. Follow school bus safety protocols during severe weather.", "ic_flood", 0);

        insertResource(db, "res_5", "Understanding School Lockdown Drills", "Drill Guides", "Guide", "3 min read",
                "Step-by-step procedure for shelter-in-place scenarios.",
                "Lockdown procedures protect students from external campus threats. When lockdown is called, move out of sight, silence all mobile devices, remain completely quiet, and follow teacher directives until law enforcement or administrators open the room.", "ic_lockdown", 0);

        // Seed Quiz Questions
        insertQuiz(db, "Fire", "What should you do FIRST when you hear the school fire alarm?",
                "Finish your current written work", "Leave belongings behind and follow teacher's evacuation instructions",
                "Run as fast as possible to the school gate", "Hide under your study desk", 1,
                "During a fire drill, leave all belongings behind and evacuate immediately in an orderly manner.");

        insertQuiz(db, "Fire", "If you encounter smoke while evacuating a burning building, you should:",
                "Stand up straight and run fast", "Crawl low on hands and knees where air is cleaner",
                "Open all windows to let smoke out", "Use the central elevator", 1,
                "Toxic smoke rises, so crawling low provides clearer air to breathe during evacuation.");

        insertQuiz(db, "Earthquake", "During an earthquake, what is the correct protective action?",
                "Run out into the corridor immediately", "Drop, Cover, and Hold On under a sturdy desk",
                "Stand next to tall glass windows", "Use the elevator to reach ground floor", 1,
                "Drop, Cover, and Hold On protects you from falling lighting, ceiling tiles, and debris.");

        insertQuiz(db, "Earthquake", "If you are outdoors on school grounds when an earthquake occurs, you should:",
                "Move to an open area away from buildings, trees, and power lines", "Run inside the nearest building",
                "Stand directly under a heavy tree for shelter", "Climb onto the roof", 0,
                "Outdoors, open ground away from structures and utility poles is safest during shaking.");

        insertQuiz(db, "General", "What is the nationwide integrated emergency number in India?",
                "100", "101", "112", "108", 2,
                "112 is the single unified emergency helpline number across India for Police, Fire, and Ambulance.");

        insertQuiz(db, "General", "After reaching the school assembly point during a drill, students must:",
                "Go home immediately", "Stay with their class and answer the roll call",
                "Re-enter the classroom to get bags", "Wander around the sports ground", 1,
                "Roll call ensures all students are accounted for and no one is missing inside the building.");

        // Seed Emergency Contacts
        insertContact(db, "National Emergency Helpline", "National", "112", "Single unified emergency number for India", "ic_call", 0);
        insertContact(db, "Fire & Rescue Services", "Fire", "101", "Immediate fire brigade emergency service", "ic_fire", 0);
        insertContact(db, "Ambulance Emergency", "Medical", "102", "Emergency medical transport service", "ic_medical", 0);
        insertContact(db, "Police Control Room", "Police", "100", "Police emergency response unit", "ic_shield", 0);
        insertContact(db, "School Administrative Office", "School", "+91 98765 43210", "Main school front desk and safety desk", "ic_school", 0);
        insertContact(db, "School Safety Coordinator", "School", "+91 98765 43211", "Designated emergency drill officer", "ic_person", 0);
        insertContact(db, "Parent / Guardian Contact", "Parent", "+91 98765 43212", "Primary family emergency contact", "ic_contacts", 0);
    }

    private void insertGuide(SQLiteDatabase db, String id, String title, String category, String summary,
                             String icon, String intro, String hazards, String prev,
                             String before, String during, String after, String warn) {
        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("title", title);
        cv.put("category", category);
        cv.put("summary", summary);
        cv.put("iconName", icon);
        cv.put("intro", intro);
        cv.put("hazards", hazards);
        cv.put("prevention", prev);
        cv.put("beforeSteps", before);
        cv.put("duringSteps", during);
        cv.put("afterSteps", after);
        cv.put("warnings", warn);
        db.insert(TABLE_SAFETY_GUIDES, null, cv);
    }

    private void insertDrill(SQLiteDatabase db, String id, String title, String type, String icon,
                             String purpose, String prep, String procedure, String dosDonts,
                             String reminders, int completed, long timestamp) {
        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("title", title);
        cv.put("type", type);
        cv.put("iconName", icon);
        cv.put("purpose", purpose);
        cv.put("prepInstructions", prep);
        cv.put("procedureSteps", procedure);
        cv.put("dosAndDonts", dosDonts);
        cv.put("safetyReminders", reminders);
        cv.put("isCompleted", completed);
        cv.put("completedTimestamp", timestamp);
        db.insert(TABLE_DRILLS, null, cv);
    }

    private void insertChecklist(SQLiteDatabase db, String category, String text, String type, int completed) {
        ContentValues cv = new ContentValues();
        cv.put("category", category);
        cv.put("text", text);
        cv.put("drillType", type);
        cv.put("isCompleted", completed);
        db.insert(TABLE_CHECKLISTS, null, cv);
    }

    private void insertResource(SQLiteDatabase db, String id, String title, String category, String type,
                                String readTime, String summary, String fullContent, String icon, int bookmarked) {
        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("title", title);
        cv.put("category", category);
        cv.put("type", type);
        cv.put("readTime", readTime);
        cv.put("summary", summary);
        cv.put("fullContent", fullContent);
        cv.put("iconName", icon);
        cv.put("isBookmarked", bookmarked);
        db.insert(TABLE_RESOURCES, null, cv);
    }

    private void insertQuiz(SQLiteDatabase db, String category, String question, String optA, String optB,
                            String optC, String optD, int correctIndex, String explanation) {
        ContentValues cv = new ContentValues();
        cv.put("category", category);
        cv.put("questionText", question);
        cv.put("optionA", optA);
        cv.put("optionB", optB);
        cv.put("optionC", optC);
        cv.put("optionD", optD);
        cv.put("correctOptionIndex", correctIndex);
        cv.put("explanation", explanation);
        db.insert(TABLE_QUIZZES, null, cv);
    }

    private void insertContact(SQLiteDatabase db, String name, String category, String phone,
                               String desc, String icon, int isCustom) {
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("category", category);
        cv.put("phoneNumber", phone);
        cv.put("description", desc);
        cv.put("iconName", icon);
        cv.put("isCustom", isCustom);
        db.insert(TABLE_CONTACTS, null, cv);
    }

    // --- Query and Modification Methods ---

    public List<SafetyGuide> getAllGuides() {
        List<SafetyGuide> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE_SAFETY_GUIDES, null, null, null, null, null, "title ASC");
        if (c != null && c.moveToFirst()) {
            do {
                list.add(new SafetyGuide(
                        c.getString(c.getColumnIndexOrThrow("id")),
                        c.getString(c.getColumnIndexOrThrow("title")),
                        c.getString(c.getColumnIndexOrThrow("category")),
                        c.getString(c.getColumnIndexOrThrow("summary")),
                        c.getString(c.getColumnIndexOrThrow("iconName")),
                        c.getString(c.getColumnIndexOrThrow("intro")),
                        c.getString(c.getColumnIndexOrThrow("hazards")),
                        c.getString(c.getColumnIndexOrThrow("prevention")),
                        c.getString(c.getColumnIndexOrThrow("beforeSteps")),
                        c.getString(c.getColumnIndexOrThrow("duringSteps")),
                        c.getString(c.getColumnIndexOrThrow("afterSteps")),
                        c.getString(c.getColumnIndexOrThrow("warnings"))
                ));
            } while (c.moveToNext());
            c.close();
        }
        return list;
    }

    public List<DrillItem> getAllDrills() {
        List<DrillItem> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE_DRILLS, null, null, null, null, null, "title ASC");
        if (c != null && c.moveToFirst()) {
            do {
                list.add(new DrillItem(
                        c.getString(c.getColumnIndexOrThrow("id")),
                        c.getString(c.getColumnIndexOrThrow("title")),
                        c.getString(c.getColumnIndexOrThrow("type")),
                        c.getString(c.getColumnIndexOrThrow("iconName")),
                        c.getString(c.getColumnIndexOrThrow("purpose")),
                        c.getString(c.getColumnIndexOrThrow("prepInstructions")),
                        c.getString(c.getColumnIndexOrThrow("procedureSteps")),
                        c.getString(c.getColumnIndexOrThrow("dosAndDonts")),
                        c.getString(c.getColumnIndexOrThrow("safetyReminders")),
                        c.getInt(c.getColumnIndexOrThrow("isCompleted")) == 1,
                        c.getLong(c.getColumnIndexOrThrow("completedTimestamp"))
                ));
            } while (c.moveToNext());
            c.close();
        }
        return list;
    }

    public void updateDrillCompletion(String drillId, boolean isCompleted) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("isCompleted", isCompleted ? 1 : 0);
        cv.put("completedTimestamp", isCompleted ? System.currentTimeMillis() : 0);
        db.update(TABLE_DRILLS, cv, "id = ?", new String[]{drillId});
    }

    public List<ChecklistItem> getChecklistByCategory(String category) {
        List<ChecklistItem> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String selection = category.equalsIgnoreCase("ALL") ? null : "category = ?";
        String[] selectionArgs = category.equalsIgnoreCase("ALL") ? null : new String[]{category.toUpperCase()};
        Cursor c = db.query(TABLE_CHECKLISTS, null, selection, selectionArgs, null, null, "id ASC");
        if (c != null && c.moveToFirst()) {
            do {
                list.add(new ChecklistItem(
                        c.getLong(c.getColumnIndexOrThrow("id")),
                        c.getString(c.getColumnIndexOrThrow("category")),
                        c.getString(c.getColumnIndexOrThrow("text")),
                        c.getString(c.getColumnIndexOrThrow("drillType")),
                        c.getInt(c.getColumnIndexOrThrow("isCompleted")) == 1
                ));
            } while (c.moveToNext());
            c.close();
        }
        return list;
    }

    public void updateChecklistItem(long itemId, boolean isCompleted) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("isCompleted", isCompleted ? 1 : 0);
        db.update(TABLE_CHECKLISTS, cv, "id = ?", new String[]{String.valueOf(itemId)});
    }

    public void resetAllChecklists() {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("isCompleted", 0);
        db.update(TABLE_CHECKLISTS, cv, null, null);
    }

    public List<LearningResource> getAllResources(String searchQuery, String categoryFilter) {
        List<LearningResource> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        StringBuilder where = new StringBuilder("1=1");
        List<String> args = new ArrayList<>();

        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            where.append(" AND (title LIKE ? OR summary LIKE ? OR fullContent LIKE ?)");
            String q = "%" + searchQuery.trim() + "%";
            args.add(q);
            args.add(q);
            args.add(q);
        }

        if (categoryFilter != null && !categoryFilter.equalsIgnoreCase("ALL")) {
            where.append(" AND category = ?");
            args.add(categoryFilter);
        }

        Cursor c = db.query(TABLE_RESOURCES, null, where.toString(),
                args.toArray(new String[0]), null, null, "title ASC");

        if (c != null && c.moveToFirst()) {
            do {
                list.add(new LearningResource(
                        c.getString(c.getColumnIndexOrThrow("id")),
                        c.getString(c.getColumnIndexOrThrow("title")),
                        c.getString(c.getColumnIndexOrThrow("category")),
                        c.getString(c.getColumnIndexOrThrow("type")),
                        c.getString(c.getColumnIndexOrThrow("readTime")),
                        c.getString(c.getColumnIndexOrThrow("summary")),
                        c.getString(c.getColumnIndexOrThrow("fullContent")),
                        c.getString(c.getColumnIndexOrThrow("iconName")),
                        c.getInt(c.getColumnIndexOrThrow("isBookmarked")) == 1
                ));
            } while (c.moveToNext());
            c.close();
        }
        return list;
    }

    public void toggleBookmark(String resourceId, boolean isBookmarked) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("isBookmarked", isBookmarked ? 1 : 0);
        db.update(TABLE_RESOURCES, cv, "id = ?", new String[]{resourceId});
    }

    public void clearAllBookmarks() {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("isBookmarked", 0);
        db.update(TABLE_RESOURCES, cv, null, null);
    }

    public List<QuizQuestion> getQuizQuestions(String category) {
        List<QuizQuestion> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String selection = (category == null || category.equalsIgnoreCase("ALL")) ? null : "category = ?";
        String[] args = (category == null || category.equalsIgnoreCase("ALL")) ? null : new String[]{category};
        Cursor c = db.query(TABLE_QUIZZES, null, selection, args, null, null, "id ASC");
        if (c != null && c.moveToFirst()) {
            do {
                list.add(new QuizQuestion(
                        c.getLong(c.getColumnIndexOrThrow("id")),
                        c.getString(c.getColumnIndexOrThrow("category")),
                        c.getString(c.getColumnIndexOrThrow("questionText")),
                        c.getString(c.getColumnIndexOrThrow("optionA")),
                        c.getString(c.getColumnIndexOrThrow("optionB")),
                        c.getString(c.getColumnIndexOrThrow("optionC")),
                        c.getString(c.getColumnIndexOrThrow("optionD")),
                        c.getInt(c.getColumnIndexOrThrow("correctOptionIndex")),
                        c.getString(c.getColumnIndexOrThrow("explanation"))
                ));
            } while (c.moveToNext());
            c.close();
        }
        return list;
    }

    public void saveQuizResult(String category, int score, int totalQuestions) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("category", category);
        cv.put("score", score);
        cv.put("totalQuestions", totalQuestions);
        cv.put("timestamp", System.currentTimeMillis());
        db.insert(TABLE_QUIZ_RESULTS, null, cv);
    }

    public List<QuizResult> getQuizResults() {
        List<QuizResult> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE_QUIZ_RESULTS, null, null, null, null, null, "timestamp DESC");
        if (c != null && c.moveToFirst()) {
            do {
                list.add(new QuizResult(
                        c.getLong(c.getColumnIndexOrThrow("id")),
                        c.getString(c.getColumnIndexOrThrow("category")),
                        c.getInt(c.getColumnIndexOrThrow("score")),
                        c.getInt(c.getColumnIndexOrThrow("totalQuestions")),
                        c.getLong(c.getColumnIndexOrThrow("timestamp"))
                ));
            } while (c.moveToNext());
            c.close();
        }
        return list;
    }

    public List<EmergencyContact> getAllContacts() {
        List<EmergencyContact> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE_CONTACTS, null, null, null, null, null, "id ASC");
        if (c != null && c.moveToFirst()) {
            do {
                list.add(new EmergencyContact(
                        c.getLong(c.getColumnIndexOrThrow("id")),
                        c.getString(c.getColumnIndexOrThrow("name")),
                        c.getString(c.getColumnIndexOrThrow("category")),
                        c.getString(c.getColumnIndexOrThrow("phoneNumber")),
                        c.getString(c.getColumnIndexOrThrow("description")),
                        c.getString(c.getColumnIndexOrThrow("iconName")),
                        c.getInt(c.getColumnIndexOrThrow("isCustom")) == 1
                ));
            } while (c.moveToNext());
            c.close();
        }
        return list;
    }

    public void updateContactNumber(long id, String newName, String newNumber) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", newName);
        cv.put("phoneNumber", newNumber);
        db.update(TABLE_CONTACTS, cv, "id = ?", new String[]{String.valueOf(id)});
    }

    public void resetAllData() {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues resetCv = new ContentValues();
        resetCv.put("isCompleted", 0);
        resetCv.put("completedTimestamp", 0);
        db.update(TABLE_DRILLS, resetCv, null, null);

        resetCv.clear();
        resetCv.put("isCompleted", 0);
        db.update(TABLE_CHECKLISTS, resetCv, null, null);

        resetCv.clear();
        resetCv.put("isBookmarked", 0);
        db.update(TABLE_RESOURCES, resetCv, null, null);

        db.delete(TABLE_QUIZ_RESULTS, null, null);
    }
}