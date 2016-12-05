package com.algonquincollege.doir0008.dungeonaccountant;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class MainActivity extends AppCompatActivity {


    //These arrays contain the basic cost logic for the skills.
    //Skills are arranged: warrior 1,2,3,  rogue 1,2,3, mage 1,2,3, and druid 1,2,3.
    //Future versions (that benefit from better planning) would use the GameCharacter class
    //for more intelligent organization and a lot of optimizations.

    //Skill cost is in the form base_cost[n]*cost_mult[n]^skill_level;
    //Time skills are in the form timer*time_effect[n]^skill_level; and
    //Gold skills are in the form base_gold*gold_effect[n]^skill_level,
    //where n is the skill #.

    //The higher the value, the more pronounced the effect.  Skills that increase
    //the result (ie: beneficial gold skills) must be >1.0.  Skills that decrease
    //the result (ie: beneficial timer skills) must be <1.0.  Neutral skills (ie:
    //timer effects from gold-only skills) must be 1.0.
    //'Harder dungeon' skills should increase the timer and the gold both.

    //Some skills (ie: warrior's "superior gear" skill) are intentionally less effective
    //than others.  This is by design.

    //In the final product, increase the base_cost and/or skill_mult values to
    //increase the final gameplay time.  Current values are currently very 'cheap' for
    //faster gameplay for demonstration purposes.

    //base_timer and and base_gold control the starting values for time and gold.
    //current_gold and timer are calculated values, and phase is a counter for which
    //graphic to show in the image view.  Note: one round is five iterations of 'timer' (in ms).


    private int current_gold=0, base_timer=6000, base_gold=1000, phase=1;
    private double timer;

    private double[] base_cost={100, 200, 150, 100, 200, 150, 200, 250, 300, 200, 300, 150};
    private double[] cost_mult={1.5f, 1.6f, 1.6f, 1.4f, 2.0f, 1.7f, 1.8f, 1.8f, 1.5f, 1.6f, 1.5f, 2.0f};
    private double[] gold_effect={1f, 1f, 1.2f, 1f, 2f, 1.3f, 1f, 1f, 1.35f, 1f, 1f, 2f};
    private double[] time_effect={0.9f, 0.95f, 1f, 0.9f, 2f, 1f, 0.9f, 0.85f, 1f, 0.9f, 0.9f, 2f};
    private int[] skills={0,0,0,0,0,0,0,0,0,0,0,0};
    private int[] R_ids=new int[12];



    private ViewSwitcher this_screen; //For ViewSwitcher logic.
    private Handler m_handler = new Handler(); //For repeating task logic.

    private Button buySkills, goBack;
    private Button warrior_1, warrior_2, warrior_3;
    private Button mage_1, mage_2, mage_3;
    private Button rogue_1, rogue_2, rogue_3;
    private Button druid_1, druid_2, druid_3;

    private TextView timeRemaining, gold, gold2;

    private TextView warriorSkill_1, warriorSkill_2, warriorSkill_3;
    private TextView rogueSkill_1, rogueSkill_2, rogueSkill_3;
    private TextView mageSkill_1, mageSkill_2, mageSkill_3;
    private TextView druidSkill_1, druidSkill_2, druidSkill_3;

    private TextView warriorSkill1, warriorSkill2, warriorSkill3;
    private TextView rogueSkill1, rogueSkill2, rogueSkill3;
    private TextView mageSkill1, mageSkill2, mageSkill3;
    private TextView druidSkill1, druidSkill2, druidSkill3;
    private ImageView progressWindow;

//  Yes, we should be using @string xml for translatability, but we're not.
    private String wtSkill1="Martial Prowess +";
    private String wtSkill2="Superior Gear +";
    private String wtSkill3="Appraise +";

    private String rtSkill1="Scouting +";
    private String rtSkill2="Guild Contacts +";
    private String rtSkill3="Smooth Talk +";

    private String mtSkill1="Superior Casting +";
    private String mtSkill2="Chronomancy +";
    private String mtSkill3="Alchemy +";

    private String dtSkill1="Forage +";
    private String dtSkill2="Combat Healing +";
    private String dtSkill3="Temple Contacts +";



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//      Hook up view.
        this_screen = (ViewSwitcher) findViewById(R.id.viewSwitcher);
        progressWindow=(ImageView)findViewById(R.id.dungeonView);


//      Hook up Textviews on Screen 1.
        warriorSkill_1 = (TextView)findViewById(R.id.warriorSkill_1);
        warriorSkill_2 = (TextView)findViewById(R.id.warriorSkill_2);
        warriorSkill_3 = (TextView)findViewById(R.id.warriorSkill_3);
        rogueSkill_1 = (TextView)findViewById(R.id.rogueSkill_1);
        rogueSkill_2 = (TextView)findViewById(R.id.rogueSkill_2);
        rogueSkill_3 = (TextView)findViewById(R.id.rogueSkill_3);
        mageSkill_1  = (TextView)findViewById(R.id.mageSkill_1);
        mageSkill_2  = (TextView)findViewById(R.id.mageSkill_2);
        mageSkill_3  = (TextView)findViewById(R.id.mageSkill_3);
        druidSkill_1 = (TextView)findViewById(R.id.druidSkill_1);
        druidSkill_2 = (TextView)findViewById(R.id.druidSkill_2);
        druidSkill_3 = (TextView)findViewById(R.id.druidSkill_3);

//      Hookups on Screen 2.
        warriorSkill1 = (TextView)findViewById(R.id.warriorSkill1);
        warriorSkill2 = (TextView)findViewById(R.id.warriorSkill2);
        warriorSkill3 = (TextView)findViewById(R.id.warriorSkill3);
        rogueSkill1 = (TextView)findViewById(R.id.rogueSkill1);
        rogueSkill2 = (TextView)findViewById(R.id.rogueSkill2);
        rogueSkill3 = (TextView)findViewById(R.id.rogueSkill3);
        mageSkill1  = (TextView)findViewById(R.id.mageSkill1);
        mageSkill2  = (TextView)findViewById(R.id.mageSkill2);
        mageSkill3  = (TextView)findViewById(R.id.mageSkill3);
        druidSkill1 = (TextView)findViewById(R.id.druidSkill1);
        druidSkill2 = (TextView)findViewById(R.id.druidSkill2);
        druidSkill3 = (TextView)findViewById(R.id.druidSkill3);

        timeRemaining = (TextView)findViewById(R.id.timeRemaining);
        gold=(TextView)findViewById(R.id.gold);
        gold2=(TextView)findViewById(R.id.gold2);

//      Hook up buttons.
        buySkills = (Button) findViewById(R.id.upgradeButton);
        buySkills.setOnClickListener(buttonManager);

        goBack=(Button)findViewById(R.id.backButton);
        goBack.setOnClickListener(buttonManager);

        warrior_1 = (Button) findViewById(R.id.warriorSkill1Btn);
        warrior_1.setOnClickListener(buttonManager);
        warrior_2 = (Button) findViewById(R.id.warriorSkill2Btn);
        warrior_2.setOnClickListener(buttonManager);
        warrior_3 = (Button) findViewById(R.id.warriorSkill3Btn);
        warrior_3.setOnClickListener(buttonManager);

        rogue_1 = (Button) findViewById(R.id.rogueSkill1Btn);
        rogue_1.setOnClickListener(buttonManager);
        rogue_2 = (Button) findViewById(R.id.rogueSkill2Btn);
        rogue_2.setOnClickListener(buttonManager);
        rogue_3 = (Button) findViewById(R.id.rogueSkill3Btn);
        rogue_3.setOnClickListener(buttonManager);

        mage_1 = (Button) findViewById(R.id.mageSkill1Btn);
        mage_1.setOnClickListener(buttonManager);
        mage_2 = (Button) findViewById(R.id.mageSkill2Btn);
        mage_2.setOnClickListener(buttonManager);
        mage_3 = (Button) findViewById(R.id.mageSkill3Btn);
        mage_3.setOnClickListener(buttonManager);

        druid_1 = (Button) findViewById(R.id.druidSkill1Btn);
        druid_1.setOnClickListener(buttonManager);
        druid_2 = (Button) findViewById(R.id.druidSkill2Btn);
        druid_2.setOnClickListener(buttonManager);
        druid_3 = (Button) findViewById(R.id.druidSkill3Btn);
        druid_3.setOnClickListener(buttonManager);

//      Set up initial screens:

        gold.setText(current_gold+"gp");
        gold2.setText(current_gold+"gp");

//Buttons to IDs:
        R_ids[0]=R.id.warriorSkill1Btn;
        R_ids[1]=R.id.warriorSkill2Btn;
        R_ids[2]=R.id.warriorSkill3Btn;
        R_ids[3]=R.id.rogueSkill1Btn;
        R_ids[4]=R.id.rogueSkill2Btn;
        R_ids[5]=R.id.rogueSkill3Btn;
        R_ids[6]=R.id.mageSkill1Btn;
        R_ids[7]=R.id.mageSkill2Btn;
        R_ids[8]=R.id.mageSkill3Btn;
        R_ids[9]=R.id.druidSkill1Btn;
        R_ids[10]=R.id.druidSkill2Btn;
        R_ids[11]=R.id.druidSkill3Btn;

        for (int i=0;i<12;i++)
        {
            fn_updateSkillScreen(i);
        }



// Setup is done!  Let's begin the game loop.
        timer=base_timer;
        startGameLoop();

    }

    Runnable m_statusChecker = new Runnable()
    {
        @Override
        public void run()
        {
            updateStatus(); //Repeating logic here.
            m_handler.postDelayed(m_statusChecker, (int)timer);
        }
    };

    public void startGameLoop()
    {
        m_statusChecker.run();
    }

    public void stopGame() //Call this for a graceful exit, or a pause.
    {
        m_handler.removeCallbacks(m_statusChecker);
    }

    public void updateStatus()
    {
        if (phase==6) //payouts!
        {
            double goldCalc=base_gold;
            timer=base_timer;
            for (int i=0;i<12;i++)
            {
                goldCalc*=Math.pow(gold_effect[i],(double)skills[i]);
                timer*=Math.pow(time_effect[i],(double)skills[i]);
            }

            current_gold+=goldCalc;
            phase=1;
            gold.setText(current_gold+"gp");
            gold2.setText(current_gold+"gp");
        }
        //Show time remaining to a tenth of a second:
        int showtime=(int)(timer/100)*(6-phase);
        timeRemaining.setText((float)showtime/10+" sec");

        switch(phase)
        {
            case 1:
                progressWindow.setImageResource(R.drawable.scene1);
                break;
            case 2:
                progressWindow.setImageResource(R.drawable.scene2);
                break;
            case 3:
                progressWindow.setImageResource(R.drawable.scene3);
                break;
            case 4:
                progressWindow.setImageResource(R.drawable.scene4);
                break;
            case 5:
                progressWindow.setImageResource(R.drawable.scene5);
                break;
        }
        phase++;
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        stopGame();
    }

    protected View.OnClickListener buttonManager = new View.OnClickListener()
    {
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.upgradeButton:
                    this_screen.showNext();
                    break;

                case R.id.backButton:
                    this_screen.showPrevious();
                    break;
            }

//      Must be a skill button then:

            for (int i=0;i<12;i++)
            {
                if (R_ids[i]==v.getId())
                {
                    fn_BuySkill(i);
                }
            }
        }
    };

    void fn_BuySkill(int index)
    {
        double cost=fn_getCost(index);
        if (cost<current_gold)
        {
            double goldCalc=1;
            double timey=1;
            for (int i=0;i<12;i++)
            {
                goldCalc=goldCalc*Math.pow(gold_effect[i],(double)skills[i]);
                timey=timey*Math.pow(time_effect[i],(double)skills[i]);
            }

            current_gold-=cost;
            gold.setText(current_gold+"gp");
            gold2.setText(current_gold+"gp");
            skills[index]++;
            fn_updateSkillScreen(index);


        }
    }

    double fn_getCost(int index)
    {
        return base_cost[index]*(double)Math.pow(cost_mult[index],skills[index]);
    }

    void fn_updateSkillScreen(int index)
    {
        Button theButton=(Button)findViewById(R_ids[index]);
        String sCost="1 for "+(int)fn_getCost(index)+"gp";

        switch (index)
        {
            case 0:
            {
                warriorSkill1.setText(wtSkill1 + (skills[index]+1));
                warriorSkill_1.setText(wtSkill1 + (skills[index]+1));
                theButton.setText(wtSkill1+sCost);
                break;
            }

            case 1:
            {
                warriorSkill2.setText(wtSkill2 + (skills[index]+1));
                warriorSkill_2.setText(wtSkill2 + (skills[index]+1));
                theButton.setText(wtSkill2+sCost);
                break;
            }

            case 2:
            {
                warriorSkill3.setText(wtSkill3 + (skills[index]+1));
                warriorSkill_3.setText(wtSkill3 + (skills[index]+1));
                theButton.setText(wtSkill3+sCost);
                break;
            }

            case 3:
            {
                rogueSkill1.setText(rtSkill1 + (skills[index]+1));
                rogueSkill_1.setText(rtSkill1 + (skills[index]+1));
                theButton.setText(rtSkill1+sCost);
                break;
            }

            case 4:
            {
                rogueSkill2.setText(rtSkill2 + (skills[index]+1));
                rogueSkill_2.setText(rtSkill2 + (skills[index]+1));
                theButton.setText(rtSkill2+sCost);
                break;
            }

            case 5:
            {
                rogueSkill3.setText(rtSkill3 + (skills[index]+1));
                rogueSkill_3.setText(rtSkill3 + (skills[index]+1));
                theButton.setText(rtSkill3+sCost);
                break;
            }

            case 6:
            {
                mageSkill1.setText(mtSkill1 + (skills[index]+1));
                mageSkill_1.setText(mtSkill1 + (skills[index]+1));
                theButton.setText(mtSkill1+sCost);
                break;
            }

            case 7:
            {
                mageSkill2.setText(mtSkill2 + (skills[index]+1));
                mageSkill_2.setText(mtSkill2 + (skills[index]+1));
                theButton.setText(mtSkill2+sCost);
                break;
            }

            case 8:
            {
                mageSkill_3.setText(mtSkill3 + (skills[index]+1));
                mageSkill3.setText(mtSkill3 + (skills[index]+1));
                theButton.setText(mtSkill3+sCost);
                break;
            }

            case 9:
            {
                druidSkill1.setText(dtSkill1 + (skills[index]+1));
                druidSkill_1.setText(dtSkill1 + (skills[index]+1));
                theButton.setText(dtSkill1+sCost);
                break;
            }

            case 10:
            {
                druidSkill2.setText(dtSkill2 + (skills[index]+1));
                druidSkill_2.setText(dtSkill2 + (skills[index]+1));
                theButton.setText(dtSkill2+sCost);
                break;
            }

            case 11:
            {
                druidSkill_3.setText(dtSkill3 + (skills[index]+1));
                druidSkill3.setText(dtSkill3 + (skills[index]+1));
                theButton.setText(dtSkill3+"1 for "+sCost);
                break;
            }
        }
    }
}



