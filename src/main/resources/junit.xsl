<?xml version="1.0" encoding="UTF-8"?>
 
<xsl:transform xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output encoding="UTF-8" method="xml" indent="yes" />
	
	<xsl:template match="/cases">
		<testsuite>
			
			
			<xsl:variable name="totalerrors" select="@errors" /> 
			
			<xsl:attribute name="errors">
				<xsl:value-of select="$totalerrors" />
			</xsl:attribute>
			
			<xsl:attribute name="timestamp">
				<xsl:value-of select="@timestamp" />
			</xsl:attribute>
			
			
			
			<xsl:attribute name="name">
				<xsl:value-of select="@name" />
			</xsl:attribute>

			<xsl:for-each select="testcase">
				<testcase>
					
				<xsl:variable name="errors" select="@errors" /> 
					
				<xsl:attribute name="classname">
					<xsl:value-of select="command" />
				</xsl:attribute>
				
				<xsl:attribute name="name">
					<xsl:value-of select="cwd" />
				</xsl:attribute>

				<xsl:if test="$errors &gt; 0">
					<failure>
						<xsl:attribute name="message" />
						<xsl:attribute name="type">1</xsl:attribute>
						<xsl:value-of select="failure" />
					</failure>
				</xsl:if>
				
				</testcase>
			</xsl:for-each>		
		
		</testsuite>
	  
	</xsl:template>
	
</xsl:transform>
