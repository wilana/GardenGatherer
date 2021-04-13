package com.WSM1120464.gardengatherer

import android.net.Uri

class Plant (
    var plantID: String?=null,
    var plantName: String?=null,
    var plantType: String?=null,
    var plantLight: String?=null,
    var plantHeight: String?= null,
    // Months as Int to allow for easier representation of ranges
    var plantBloomStart: Int?=null,
    var plantBloomEnd: Int?=null,
    var plantFertilizer: String?=null,
    var plantPruning: String?=null,
    var plantNotes: String?=null,
    var plantImageUri: Uri?=null,
    var gardenID: String?=null
)
