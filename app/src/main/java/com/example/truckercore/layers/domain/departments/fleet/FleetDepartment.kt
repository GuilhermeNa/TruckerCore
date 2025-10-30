package com.example.truckercore.layers.domain.departments.fleet

import com.example.truckercore.core.error.DomainException
import com.example.truckercore.layers.domain.base.others.Plate
import com.example.truckercore.layers.domain.departments.fleet.collections.RigCollection
import com.example.truckercore.layers.domain.departments.fleet.collections.TrailerCollection
import com.example.truckercore.layers.domain.departments.fleet.collections.TruckCollection
import com.example.truckercore.layers.domain.departments.fleet.objects.Rig
import com.example.truckercore.layers.domain.model.hitch.Hitch
import com.example.truckercore.layers.domain.model.trailer.Trailer
import com.example.truckercore.layers.domain.model.truck.Truck

class FleetDepartment {

    private val rigs = RigCollection()

    private val trucks = TruckCollection()

    private val trailers = TrailerCollection()

    //----------------------------------------------------------------------------------------------
    // Initialize
    //----------------------------------------------------------------------------------------------
    fun initFromDatabase(truckList: List<Truck>, trailerList: List<Trailer>) {
        registerTrucks(truckList)
        registerTrailers(trailerList)
        assembleRigs()
    }

    private fun registerTrucks(truckList: List<Truck>) {
        truckList.forEach { truck ->
            if (trucks.contains(truck.plate)) {
                throw DomainException.RuleViolation(REGISTER_TRUCK_ERROR_MSG)
            } else trucks.add(truck)
        }
    }

    private fun registerTrailers(trailerList: List<Trailer>) {
        trailerList.forEach { trailer ->
            if (trailers.contains(trailer.plate)) {
                throw DomainException.RuleViolation(REGISTER_TRAILER_ERROR_MSG)
            } else trailers.add(trailer)
        }
    }

    private fun assembleRigs() {
        val mRigs = trucks.data.mapNotNull { mTruck ->
            val hitch = mTruck.getCurrentHitch() ?: return@mapNotNull null
            val mTrailers = getTrailers(hitch)
            Rig(mTruck, mTrailers)
        }
        rigs.addAll(mRigs)
    }

    private fun getTrailers(hitch: Hitch): Set<Trailer> =
        hitch.trailerIds.map {
            trailers.findBy(it) ?: throw DomainException.RuleViolation(TRAILER_NOT_FOUND_ERROR_MSG)
        }.toSet()

    //----------------------------------------------------------------------------------------------
    //
    //----------------------------------------------------------------------------------------------

    fun findRigBy(plate: Plate): Rig? = rigs.findBy(plate)

    fun findTruckBy(plate: Plate): Truck? = trucks.findBy(plate)

    fun findTrailerBy(plate: Plate): Trailer? = trailers.findBy(plate)

    companion object {

        private const val TRAILER_NOT_FOUND_ERROR_MSG = "Trailer not found for hitch."

        private const val REGISTER_TRUCK_ERROR_MSG =
            "Cannot register Truck: a truck with the same plate already exists."

        private const val REGISTER_TRAILER_ERROR_MSG =
            "Cannot register Trailer: a trailer with the same plate already exists."

    }

}