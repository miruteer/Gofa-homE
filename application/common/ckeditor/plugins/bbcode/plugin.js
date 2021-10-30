/**
 * @license Copyright (c) 2003-2018, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or https://ckeditor.com/legal/ckeditor-oss-license
 */

( function() {
	CKEDITOR.on( 'dialogDefinition', function( ev ) {
		var tab,
			name = ev.data.name,
			definition = ev.data.definition;

		if ( name == 'link' ) {
			definition.removeContents( 'target' );
			definition.removeContents( 'upload' );
			definition.removeContents( 'advanced' );
			tab = definition.getContents( 'info' );
			tab.remove( 'emailSubject' );
			tab.remove( 'emailBody' );
		} else if ( name == 'image' ) {
			definition.removeContents( 'advanced' );
			tab = definition.getContents( 'Link' );
			tab.remove( 'cmbTarget' );
			tab = definition.getContents( 'info' );
			tab.remove( 'txtAlt' );
			tab.remove( 'basic' );
		}
	} );

	var bbcodeMap = { b: 'strong', u: 'u', i: 'em', color: 'span', size: 'span', left: 'div', right: 'div', center: 'div', justify: 'div', quote: 'blockquote', code: 'code', url: 'a', email: 'span', img: 'span', '*': 'li', list: 'ol' },
		convertMap = { strong: 'b', b: 'b', u: 'u', em: 'i', i: 'i', code: 'code', li: '*' },
		tagnameMap = { strong: 'b', em: 'i', u: 'u', li: '*', ul: 'list', ol: 'list', code: 'code', a: 'link', img: 'img', blockquote: 'quote' },
		stylesMap = { color: 'color', size: 'font-size', left: 'text-align', center: 'text-align', right: 'text-align', justify: 'text-align' },
		attributesMap = { url: 'href', email: 'mailhref', quote: 'cite', list: 'listType' };

	// List of block-like tags.
	var dtd = CKEDITOR.dtd,
		blockLikeTags = CKEDITOR.tools.extend( { table: 1 }, dtd.$block, dtd.$listItem, dtd.$tableContent, dtd.$list );

	var semicolonFixRegex = /\s*(?:;\s*|$)/;

	function serializeStyleText( stylesObject ) {
		var styleText = '';
		for ( var style in stylesObject ) {
			var styleVal = stylesObject[ style ],
				text = ( style + ':' + styleVal ).replace( semicolonFixRegex, ';' );

			styleText += text;
		}
		return styleText;
	}

	

	var decodeHtml = ( function() {
		var regex = [],
			entities = {
				nbsp: '\u00A0', // IE | FF
				shy: '\u00AD' // IE
			};

		for ( var entity in entities )
			regex.push( entity );

		regex = new RegExp( '&(' + regex.join( '|' ) + ');', 'g' );

		return function( html ) {
			return html.replace( regex, function( match, entity ) {
				return entities[ entity ];
			} );
		};
	} )();

	CKEDITOR.BBCodeParser = function() {
		this._ = {
			bbcPartsRegex: /(?:\[([^\/\]=]*?)(?:=([^\]]*?))?\])|(?:\[\/([a-z]{1,16})\])/ig
		};
	};

	CKEDITOR.BBCodeParser.prototype = {
		parse: function( bbcode ) {
			var parts, part,
				lastIndex = 0;

			while ( ( parts = this._.bbcPartsRegex.exec( bbcode ) ) ) {
				var tagIndex = parts.index;
				if ( tagIndex > lastIndex ) {
					var text = bbcode.substring( lastIndex, tagIndex );
					this.onText( text, 1 );
				}

				lastIndex = this._.bbcPartsRegex.lastIndex;

				// "parts" is an array with the following items:
				// 0 : The entire match for opening/closing tags and line-break;
				// 1 : line-break;
				// 2 : open of tag excludes option;
				// 3 : tag option;
				// 4 : close of tag;

				part = ( parts[ 1 ] || parts[ 3 ] || '' ).toLowerCase();
				// Unrecognized tags should be delivered as a simple text (https://dev.ckeditor.com/ticket/7860).
				if ( part && !bbcodeMap[ part ] ) {
					this.onText( parts[ 0 ] );
					continue;
				}

				// Opening tag
				if ( parts[ 1 ] ) {
					var tagName = bbcodeMap[ part ],
						attribs = {},
						styles = {},
						optionPart = parts[ 2 ];

					// Special handling of justify tags, these provide the alignment as a tag name (#2248).
					if ( part == 'left' || part == 'right' || part == 'center' || part == 'justify' ) {
						optionPart = part;
					}

					if ( optionPart ) {
						if ( part == 'list' ) {
							if ( !isNaN( optionPart ) )
								optionPart = 'decimal';
							else if ( /^[a-z]+$/.test( optionPart ) )
								optionPart = 'lo