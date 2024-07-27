package com.dedan.rekowisatabali.model

import kotlinx.serialization.Serializable

@Serializable
data class RecommendationMetric(
    val limit: Int,
    val cities_id: Set<Int>,
    val inp_l3_cg1_a: Float,
    val inp_l3_cg1_b: Float,
    val inp_l3_cg2_a: Float,
    val inp_l3_cg2_b: Float,
    val inp_l3_cg2_c: Float,
    val inp_l3_cg3_a: Float,
    val inp_l3_cg3_b: Float,
    val inp_l3_cg3_c: Float,
    val inp_l2_cg1_a: Float,
    val inp_l2_cg1_b: Float,
    val inp_l2_cg1_c: Float,
    val inp_l1_a: Float,
    val inp_l1_b: Float,
    val inp_l1_c: Float,
    val l1_b_direction: Int,
    val l1_c_direction: Int
)
