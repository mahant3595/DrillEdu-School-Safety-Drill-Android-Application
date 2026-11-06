package com.example.drilledu;

import com.example.drilledu.models.AchievementItem;
import com.example.drilledu.models.ChecklistItem;
import com.example.drilledu.models.DrillItem;
import com.example.drilledu.models.IncidentReport;
import com.example.drilledu.models.QuizQuestion;
import com.example.drilledu.models.QuizResult;

import org.junit.Test;

import static org.junit.Assert.*;

public class DrillEduUnitTest {

    @Test
    public void testDrillCompletionModel() {
        DrillItem drill = new DrillItem("drill_1", "Fire Evacuation Drill", "Fire", "ic_fire",
                "Practice evacuation", "Prep well", "1. Line up\n2. Walk out",
                "Do: Walk. Don't: Run.", "Stay calm", false, 0);

        assertFalse(drill.isCompleted());
        drill.setCompleted(true);
        assertTrue(drill.isCompleted());
        assertEquals("Fire Evacuation Drill", drill.getTitle());
    }

    @Test
    public void testChecklistItemModel() {
        ChecklistItem item = new ChecklistItem(1, "BEFORE", "Know emergency exits", "General", false);
        assertFalse(item.isCompleted());
        item.setCompleted(true);
        assertTrue(item.isCompleted());
        assertEquals("BEFORE", item.getCategory());
    }

    @Test
    public void testQuizScoringCalculation() {
        QuizQuestion question = new QuizQuestion(1, "Fire", "What to do on fire alarm?",
                "Option A", "Option B", "Option C", "Option D", 1, "Explanation here");

        assertEquals(1, question.getCorrectOptionIndex());

        QuizResult result = new QuizResult(1, "Fire", 4, 5, System.currentTimeMillis());
        assertEquals(80, result.getPercentage());
    }

    @Test
    public void testIncidentReportModel() {
        IncidentReport report = new IncidentReport(1, "Blocked Stairwell", "Exit Blocked",
                "Science Block 2nd Floor", "HIGH", "Boxes blocking exit path", System.currentTimeMillis(), "LOGGED");

        assertEquals("Blocked Stairwell", report.getTitle());
        assertEquals("HIGH", report.getUrgency());
        assertEquals("LOGGED", report.getStatus());
    }

    @Test
    public void testAchievementModel() {
        AchievementItem achievement = new AchievementItem("ach_1", "First Responder",
                "Complete first drill", "ic_achievement", false, 0, 0, 1);

        assertFalse(achievement.isUnlocked());
        achievement.setUnlocked(true);
        assertTrue(achievement.isUnlocked());
    }
}