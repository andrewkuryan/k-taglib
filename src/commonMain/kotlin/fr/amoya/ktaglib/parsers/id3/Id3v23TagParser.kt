package fr.amoya.ktaglib.parsers.id3

import fr.amoya.ktaglib.parsers.TagParser
import fr.amoya.ktaglib.parsers.abstracts.AbstractSequentialTagParser


/*
* fr.amoya.ktaglib.parsers.id3
* As a part of the Project k-taglib
* @Author Arnaud Moya : <contact@amoya.fr>
* Created on 30/04/2021
*/


class Id3v23TagParser(rawData: ByteArray) : TagParser, AbstractSequentialTagParser(rawData)
{
}