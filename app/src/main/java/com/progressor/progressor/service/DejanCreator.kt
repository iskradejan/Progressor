package com.progressor.progressor.service

import com.progressor.progressor.model.dataobjects.workout.dejan.*

class DejanCreator {
    fun createDayOne(
            inclineChestPress: LinkedHashMap<HashMap<Int, Int>, Int>,
            flatChestPress: LinkedHashMap<HashMap<Int, Int>, Int>,
            chestFlies: LinkedHashMap<HashMap<Int, Int>, Int>,
            bicepCurls: LinkedHashMap<HashMap<Int, Int>, Int>,
            hammerCurls: LinkedHashMap<HashMap<Int, Int>, Int>,
            barbellRollout: LinkedHashMap<Int, Int>,
            flutterKick: LinkedHashMap<Int, Int>,
            starPlank: LinkedHashMap<Int, Int>
    ): DayOne {

        return DayOne(
                inclineChestPress = inclineChestPress,
                flatChestPress = flatChestPress,
                chestFlies = chestFlies,
                bicepCurls = bicepCurls,
                hammerCurls = hammerCurls,
                barbellRollout = barbellRollout,
                flutterKick = flutterKick,
                starPlank = starPlank
        )
    }

    fun createDayTwo(elliptical: Int): DayTwo {
        return DayTwo(elliptical = elliptical)
    }

    fun createDayThree(
            deadlift: LinkedHashMap<HashMap<Int, Int>, Int>,
            wideGripPullDown: LinkedHashMap<HashMap<Int, Int>, Int>,
            seatedCableRow: LinkedHashMap<HashMap<Int, Int>, Int>,
            hyperExtension: LinkedHashMap<HashMap<Int, Int>, Int>,
            tricepExtension: LinkedHashMap<HashMap<Int, Int>, Int>,
            dips: LinkedHashMap<HashMap<Int, Int>, Int>,
            dumbbellKickback: LinkedHashMap<HashMap<Int, Int>, Int>,
            skullcrushers: LinkedHashMap<HashMap<Int, Int>, Int>,
            legRaise: LinkedHashMap<Int, Int>,
            plank: LinkedHashMap<Int, Int>,
            sidePlank: LinkedHashMap<Int, Int>,
            cableCrunch: LinkedHashMap<HashMap<Int, Int>, Int>
    ): DayThree {
        return DayThree(
                deadlift = deadlift,
                wideGripPullDown = wideGripPullDown,
                seatedCableRow = seatedCableRow,
                hyperExtension = hyperExtension,
                tricepExtension = tricepExtension,
                dips = dips,
                dumbbellKickback = dumbbellKickback,
                skullcrushers = skullcrushers,
                legRaise = legRaise,
                plank = plank,
                cableCrunch = cableCrunch
        )
    }

    fun createDayFour(arcMaster: Int): DayFour {
        return DayFour(arcMaster = arcMaster)
    }

    fun createDayFive(
            squats: LinkedHashMap<HashMap<Int, Int>, Int>,
            romanianDeadlift: LinkedHashMap<HashMap<Int, Int>, Int>,
            pistolSquats: LinkedHashMap<HashMap<Int, Int>, Int>,
            lunges: LinkedHashMap<HashMap<Int, Int>, Int>,
            cableFrontRaise: LinkedHashMap<HashMap<Int, Int>, Int>,
            pushPress: LinkedHashMap<HashMap<Int, Int>, Int>,
            dumbbellLateralRaise: LinkedHashMap<HashMap<Int, Int>, Int>,
            hangingLegRaise: LinkedHashMap<Int, Int>,
            bicycles: LinkedHashMap<Int, Int>,
            russianTwists: LinkedHashMap<Int, Int>
    ): DayFive {
        return DayFive(
                squats = squats,
                romanianDeadlift = romanianDeadlift,
                pistolSquats = pistolSquats,
                lunges = lunges,
                cableFrontRaise = cableFrontRaise,
                pushPress = pushPress,
                dumbbellLateralRaise = dumbbellLateralRaise,
                hangingLegRaise = hangingLegRaise,
                bicycles = bicycles,
                russianTwists = russianTwists
        )
    }
}