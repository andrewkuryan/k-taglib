package fr.amoya.ktaglib.common.tags.id3v2.frame.contentFrames

import fr.amoya.ktaglib.common.tags.id3v2.frame.Id3FrameContent


/*
* fr.amoya.ktaglib.common.tags.id3v2.frame.contentFrames
* As a part of the Project k-taglib
* @Author Arnaud Moya : <contact@amoya.fr>
* Created on 05/05/2021
*/


data class Id3FrameContentURL(
  var url: String
) : Id3FrameContent
{
  override fun getContentAsString(): String = url
}

