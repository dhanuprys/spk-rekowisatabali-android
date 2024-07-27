package com.dedan.rekowisatabali.model

data class Criteria(
    val tag: String,
    val text: String,
    val withDirection: Boolean = false,
    val direction: Int = 1
) {
    companion object {
        val Level3_1: List<Criteria> = listOf(
            Criteria(
                tag = "l3_cg1_a",
                text = "Kondisi Alam"
            ),
            Criteria(
                tag = "l3_cg1_b",
                text = "Kondisi Budaya"
            )
        )

        val Level3_2: List<Criteria> = listOf(
            Criteria(
                tag = "l3_cg2_a",
                text = "Lingkungan"
            ),
            Criteria(
                tag = "l3_cg2_b",
                text = "Infrastruktur"
            ),
            Criteria(
                tag = "l3_cg2_c",
                text = "Aksesebilitas"
            )
        )

        val Level3_3: List<Criteria> = listOf(
            Criteria(
                tag = "l3_cg3_a",
                text = "Kelembagaan Pengelola"
            ),
            Criteria(
                tag = "l3_cg3_b",
                text = "SDM Pengelola"
            ),
            Criteria(
                tag = "l3_cg3_c",
                text = "Tata Kehidupan Masyarakat"
            ),
        )

        val Level2: List<Criteria> = listOf(
            Criteria(
                tag = "l2_cg1_a",
                text = "Daya Tarik"
            ),
            Criteria(
                tag = "l2_cg1_b",
                text = "Kondisi Fisik"
            ),
            Criteria(
                tag = "l2_cg1_c",
                text = "Organisasi Pengelola"
            ),
        )

        val Level1: List<Criteria> = listOf(
            Criteria(
                tag = "l1_a",
                text = "Aspek Wisata"
            ),
            Criteria(
                tag = "l1_b",
                text = "Jarak Dari Pusat Kota",
                withDirection = true
            ),
            Criteria(
                tag = "l1_c",
                text = "Kepadatan Penduduk",
                withDirection = true
            ),
        )
    }
}