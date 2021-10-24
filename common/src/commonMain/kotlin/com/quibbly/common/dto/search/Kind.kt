package com.quibbly.common.dto.search

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = KindSerializer::class)
enum class Kind(val value: String) {
    book("book"),
    album("album"),
    coachedAudio("coached-audio"),
    featureMovie("feature-movie"),
    interactiveBooklet("interactive-booklet"),
    musicVideo("music-video"),
    pdfPodcast("pdf podcast"),
    podcastEpisode("podcast-episode"),
    softwarePackage("software-package"),
    song("song"),
    tvEpisode("tv-episode"),
    artistFor("artistFor"),
    unknown(""),
}

object KindSerializer : KSerializer<Kind> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Kind", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Kind) {
        encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): Kind {
        val kindString = decoder.decodeString()
        return try {
            Kind.valueOf(kindString)
        } catch (e: IllegalArgumentException) {
            Kind.unknown
        }
    }
}