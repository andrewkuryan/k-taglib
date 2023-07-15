package fr.amoya.ktaglib.tag

import fr.amoya.ktaglib.utils.Utils
import korlibs.io.file.VfsFile

/*
* fr.amoya.ktaglib.tags
* As a part of the Project k-taglib
* @Author Arnaud Moya : <contact@amoya.fr>
* Created on 01/05/2021
*/

interface Tag {
    val tagVersion: TagType
    val title: String?
    val artist: String?
    val album: String?
    val year: String?
    val comment: String?
    val genre: String?

    val frames: Collection<Frame>

    operator fun get(frameId: KnownFrame): String? =
        frames.find { frameId == it.id }?.content?.getContentAsString()

    companion object {
        @ExperimentalUnsignedTypes
        suspend fun getTag(file: VfsFile): Tag {
            val bytes = file.readBytes()
            return TagParser.getParser(Utils.getTagSpec(bytes)).parse(bytes)
        }
    }
}

