package com.team1678.frc2022.subsystems;

import com.team1678.frc2022.loops.Loop;

import edu.wpi.first.wpilibj.Encoder.IndexingType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.team1678.frc2022.loops.ILooper;

public class Superstructure extends Subsystem {

    /* Superstructure Instance */
    private static Superstructure mInstance;

    public synchronized static Superstructure getInstance() {
        if (mInstance == null) {
            mInstance = new Superstructure();
        }

        return mInstance;
    };

    /* Required Subsystem Instances */
    // private final Hood mHood = Hood.getInstance();
    private final Shooter mShooter = Shooter.getInstance();
    private final Indexer mIndexer = Indexer.getInstance();
    private final Intake mIntake = Intake.getInstance();

    /* Status Variables */
    public boolean mWantsSpinUp = false;
    public boolean mWantsShoot = false;

    @Override
    public void registerEnabledLoops(ILooper enabledLooper) {
        enabledLooper.register(new Loop() {
            @Override
            public void onStart(double timestamp) {

            }

            @Override
            public void onLoop(double timestamp) {
                setSetpoints();
                SmartDashboard.putBoolean("Want Spin Up", mWantsSpinUp);
                SmartDashboard.putBoolean("Want Shoot", mWantsShoot);
                SmartDashboard.putBoolean("Is Spun Up", isSpunUp());
            }

            @Override
            public void onStop(double timestamp) {

            }
        });
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub

    }

    public boolean isSpunUp() {
        return mShooter.spunUp();
    }

    public void setWantSpinUp() {
        mWantsSpinUp = !mWantsSpinUp;
    }

    public void setWantShoot() {
        mWantsShoot = !mWantsShoot;
    }

    public void setSetpoints() {
        /* Default indexer wanted action to be set */
        Indexer.WantedAction real_indexer;

        if (mWantsSpinUp) {
            mShooter.setVelocity(2000);
        } else {
            mShooter.setOpenLoop(0.0);
        }

        if (mWantsShoot) {
            if (isSpunUp()) {
                real_indexer = Indexer.WantedAction.FEED;
            } else {
                real_indexer = Indexer.WantedAction.NONE;
            }
        } else {
            real_indexer = Indexer.WantedAction.NONE;
        }

        /* SET INDEXER STATE */
        if (mIntake.getState() == Intake.State.INTAKING) {
            mIndexer.setState(Indexer.WantedAction.INDEX);
        } else if (mIntake.getState() == Intake.State.REVERSING) {
            mIndexer.setState(Indexer.WantedAction.REVERSE);
        } else {
            mIndexer.setState(real_indexer);
        }
    }

    @Override
    public boolean checkSystem() {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * // Subsystem Instance
     * private final Climber mClimber = Climber.getInstance();
     * 
     */
}