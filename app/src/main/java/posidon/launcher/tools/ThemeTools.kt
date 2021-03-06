package posidon.launcher.tools

import android.content.res.Resources
import android.content.res.XmlResourceParser
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.util.*
import kotlin.collections.HashMap

object ThemeTools {

    class IconPackInfo {
        var scaleFactor = 1f
        val iconResourceNames = HashMap<String, String>()
        var iconBack: String? = null
        var iconMask: String? = null
        var iconFront: String? = null
    }

    fun getIconPackInfo(res: Resources, iconPackName: String): IconPackInfo {
        val info = IconPackInfo()
        try {
            val n = res.getIdentifier("appfilter", "xml", iconPackName)
            if (n != 0) {
                val xrp = res.getXml(n)
                while (xrp.eventType != XmlResourceParser.END_DOCUMENT) {
                    if (xrp.eventType == 2) {
                        try {
                            when (xrp.name) {
                                "scale" -> {
                                    info.scaleFactor = xrp.getAttributeValue(0).toFloat()
                                }
                                "item" -> {
                                    info.iconResourceNames[xrp.getAttributeValue(0)] = xrp.getAttributeValue(1)
                                }
                                "iconback" -> info.iconBack = xrp.getAttributeValue(0)
                                "iconmask" -> info.iconMask = xrp.getAttributeValue(0)
                                "iconupon" -> info.iconFront = xrp.getAttributeValue(0)
                            }
                        } catch (e: Exception) {}
                    }
                    xrp.next()
                }
            } else {
                val factory = XmlPullParserFactory.newInstance()
                factory.isValidating = false
                val xpp = factory.newPullParser()
                val raw = res.assets.open("appfilter.xml")
                xpp.setInput(raw, null)
                while (xpp.eventType != XmlPullParser.END_DOCUMENT) {
                    if (xpp.eventType == 2) {
                        try {
                            when (xpp.name) {
                                "scale" -> {
                                    info.scaleFactor = xpp.getAttributeValue(0).toFloat()
                                }
                                "item" -> {
                                    info.iconResourceNames[xpp.getAttributeValue(0)] = xpp.getAttributeValue(1)
                                }
                                "iconback" -> info.iconBack = xpp.getAttributeValue(0)
                                "iconmask" -> info.iconMask = xpp.getAttributeValue(0)
                                "iconupon" -> info.iconFront = xpp.getAttributeValue(0)
                            }
                        } catch (e: Exception) {}
                    }
                    xpp.next()
                }
            }
        } catch (ignore: Exception) {}
        return info
    }

    fun getResourceNames(res: Resources, iconPack: String?): ArrayList<String> {
        val strings = ArrayList<String>()
        try {
            val n = res.getIdentifier("appfilter", "xml", iconPack)
            if (n != 0) {
                val xrp = res.getXml(n)
                while (xrp.eventType != XmlResourceParser.END_DOCUMENT) {
                    try { if (xrp.eventType == 2 && !strings.contains(xrp.getAttributeValue(1))) if (xrp.name == "item") strings.add(xrp.getAttributeValue(1)) }
                    catch (ignore: Exception) {}
                    xrp.next()
                }
            } else {
                val factory = XmlPullParserFactory.newInstance()
                factory.isValidating = false
                val xpp = factory.newPullParser()
                val raw = res.assets.open("appfilter.xml")
                xpp.setInput(raw, null)
                while (xpp!!.eventType != XmlPullParser.END_DOCUMENT) {
                    try { if (xpp.eventType == 2 && !strings.contains(xpp.getAttributeValue(1))) if (xpp.name == "item") strings.add(xpp.getAttributeValue(1)) }
                    catch (ignore: Exception) {}
                    xpp.next()
                }
            }
        } catch (ignore: Exception) {}
        return strings
    }

    /*
    fun getScaleFactor(res: Resources?, string: String?): Float {
        try {
            val n = res!!.getIdentifier("appfilter", "xml", string)
            if (n != 0) {
                val xrp = res.getXml(n)
                while (xrp.eventType != XmlResourceParser.END_DOCUMENT) {
                    if (xrp.eventType == 2) {
                        try { if (xrp.name == "scale") return xrp.getAttributeValue(0).toFloat() }
                        catch (ignore: Exception) {}
                    }
                    xrp.next()
                }
            } else {
                val factory = XmlPullParserFactory.newInstance()
                factory.isValidating = false
                val xpp = factory.newPullParser()
                val raw = res.assets.open("appfilter.xml")
                xpp.setInput(raw, null)
                while (xpp!!.eventType != XmlPullParser.END_DOCUMENT) {
                    if (xpp.eventType == 2) {
                        try { if (xpp.name == "scale") return xpp.getAttributeValue(0).toFloat() }
                        catch (ignore: Exception) {}
                    }
                    xpp.next()
                }
            }
        } catch (ignore: Exception) {}
        return 1f
    }
    fun getResourceName(res: Resources?, iconPack: String?, componentInfo: String?): String? {
        try {
            val n = res!!.getIdentifier("appfilter", "xml", iconPack)
            if (n != 0) {
                val xrp = res.getXml(n)
                while (xrp.eventType != XmlResourceParser.END_DOCUMENT) {
                    if (xrp.eventType == 2) {
                        try { if (xrp.name == "item" && xrp.getAttributeValue(0).compareTo(componentInfo!!) == 0) return xrp.getAttributeValue(1) }
                        catch (ignore: Exception) {}
                    }
                    xrp.next()
                }
            } else {
                val factory = XmlPullParserFactory.newInstance()
                factory.isValidating = false
                val xpp = factory.newPullParser()
                val raw = res.assets.open("appfilter.xml")
                xpp.setInput(raw, null)
                while (xpp!!.eventType != XmlPullParser.END_DOCUMENT) {
                    if (xpp.eventType == 2) {
                        try { if (xpp.name == "item" && xpp.getAttributeValue(0).compareTo(componentInfo!!) == 0) return xpp.getAttributeValue(1) }
                        catch (ignore: Exception) {}
                    }
                    xpp.next()
                }
            }
        } catch (ignore: Exception) {}
        return null
    }
    fun getIconBackAndMaskResourceName(res: Resources, packageName: String?): Array<String?> {
        val resource = arrayOfNulls<String>(3)
        try {
            val n = res.getIdentifier("appfilter", "xml", packageName)
            if (n != 0) {
                val xrp = res.getXml(n)
                while (xrp.eventType != XmlResourceParser.END_DOCUMENT && (resource[0] == null || resource[1] == null || resource[2] == null)) {
                    if (xrp.eventType == 2) {
                        try {
                            when (xrp.name) {
                                "iconback" -> resource[0] = xrp.getAttributeValue(0)
                                "iconmask" -> resource[1] = xrp.getAttributeValue(0)
                                "iconupon" -> resource[2] = xrp.getAttributeValue(0)
                            }
                        } catch (ignore: Exception) {}
                    }
                    xrp.next()
                }
            } else {
                val factory = XmlPullParserFactory.newInstance()
                factory.isValidating = false
                val xpp = factory.newPullParser()
                val raw = res.assets.open("appfilter.xml")
                xpp.setInput(raw, null)
                while (xpp!!.eventType != XmlPullParser.END_DOCUMENT && (resource[0] == null || resource[1] == null || resource[2] == null)) {
                    if (xpp.eventType == 2) {
                        try {
                            when (xpp.name) {
                                "iconback" -> resource[0] = xpp.getAttributeValue(0)
                                "iconmask" -> resource[1] = xpp.getAttributeValue(0)
                                "iconupon" -> resource[2] = xpp.getAttributeValue(0)
                            }
                        } catch (ignore: Exception) {}
                    }
                    xpp.next()
                }
            }
        } catch (ignore: Exception) {}
        return resource
    }
    */
}