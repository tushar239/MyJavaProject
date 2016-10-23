<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="/">
		<html>
			<body>
				<h2>My CD Collection</h2>
				<xsl:apply-templates select="catalog/cd" />
				<xsl:call-template name="footer" />
			</body>
		</html>
	</xsl:template>

	<xsl:template match="cd">
		<p>
			<xsl:apply-templates select="title" />
			<xsl:apply-templates select="artist" />
			<xsl:call-template name="add_your_name" />
		</p>
	</xsl:template>

	<xsl:template match="title">
		Title:
		<span style="color:#ff0000">
			<xsl:value-of select="." />
		</span>
		<br />
	</xsl:template>

	<xsl:template match="artist">
		Artist:
		<span style="color:#00ff00">
			<xsl:value-of select="." />
		</span>
		<br />
	</xsl:template>

	<xsl:template name="add_your_name">
		Add Your Name:
		<input type="text">
			<xsl:attribute name="value">
			 	<xsl:value-of select="artist" />
			 </xsl:attribute>
		</input>
		<br />
	</xsl:template>
	
	<xsl:template name="footer">
		<p>
			I am a footer.
		</p>
	</xsl:template>
</xsl:stylesheet>