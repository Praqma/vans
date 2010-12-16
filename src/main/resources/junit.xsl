<?xml version="1.0" encoding="UTF-8"?>
 
<xsl:transform xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output encoding="UTF-8" method="xml" indent="yes" />
	
	<xsl:template match="/cases">
		<testsuite>
			
			<xsl:attribute name="errors">
				<xsl:value-of select="@errors" />
			</xsl:attribute>
			
			<xsl:attribute name="name">
				<xsl:value-of select="@name" />
			</xsl:attribute>

			<xsl:for-each select="testcase">
				<testcase>
					
				<xsl:attribute name="classname">
					<xsl:value-of select="command" />
				</xsl:attribute>
				
				<failure>
					<xsl:attribute name="message" />
					<xsl:attribute name="type">1</xsl:attribute>
				</failure>
				
				</testcase>
			</xsl:for-each>		
		
		</testsuite>
	  
	</xsl:template>
	
</xsl:transform>
