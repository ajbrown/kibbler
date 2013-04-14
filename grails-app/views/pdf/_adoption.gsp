<%--
  Created by IntelliJ IDEA.
  User: ajbrown
  Date: 4/6/13
  Time: 10:53 PM
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
    <title>Adoption </title>
    <style>
    @page {
        size: 210mm 297mm; /* A4 format */
        @bottom-center { content: element(footer);}
        @top-center { content: element(header); }
    }

    div.break {
        page-break-after:always;
    }

    body, html, p, h1, h2, h3, span, div {
        font: normal "Georgia Pro",georgia,serif
    }

    h1 {
        font-size: 36pt;
    }

    p, ol {
        font-size: 12pt;
        line-height: 18pt;
    }

    .form-section {
        width: 100%;
        margin: 0;
        padding: 0;
        page-break-inside: avoid;
    }

    .form-item {
        border: 1px dotted #CCC;
        border-bottom: 1.5px solid black;
        padding: 0 5px;
        width: 100%;
        height: 36px;
        margin: 3px 0px;
        display: inline-block;
        overflow-x: hidden;
    }

    .form-item strong, .signature-item strong {
        margin: 0;
        text-transform: uppercase;
        font-size: 8pt;
        left: -4;
        top: -2;
        font-family: sans-serif;
        width: 100%;
        display: block;
    }

    .form-item .value {
        font-family: monospace;
        font-size: 10pt;
        position: relative;
        width: 100%;
        clear:left;
    }

    .signature-item {
        margin-top: 80px;
        border-top: 2px solid black;
        display: inline-block;
    }

    .signature-item strong {
        margin-top: 4px;
        margin-left: 8px;
    }


    </style>
</head>
<body>

<div id="header"></div>

<h1>Pet Adoption Contract</h1>

<div class="form-section">
    <div class="form-item" style="width: 424px;">
        <strong>Animal: </strong>
        <span class="value">Fake Ass Mikka</span>
    </div>

    <div class="form-item" style="width: 100px;">
        <strong class="label">Age: </strong>
        <span class="value">4</span>
    </div>

    <div class="form-item" style="width: 100px;">
        <strong>Sex: </strong>
        <span class="value">Female</span>
    </div>

    <div class="form-item" style="width: 250px;">
        <strong>Breed: </strong>
        <span class="value">Rottweiler</span>
    </div>

    <div class="form-item" style="width: 390px;">
        <strong>Colors/Markings: </strong>
        <span class="value">Black/Tan</span>
    </div>

</div>

<p>As an Adopter, I agree to the following terms:</p>

<ol>
    <li>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed semper, mauris non pulvinar ullamcorper, felis leo aliquet velit, vitae facilisis mi arcu eu elit. Pellentesque tincidunt, sapien eget porttitor rhoncus, elit eros pulvinar mauris, at pharetra lectus ante ut mauris.</li>
    <li>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed semper, mauris non pulvinar ullamcorper, felis leo aliquet velit, vitae facilisis mi arcu eu elit. Pellentesque tincidunt, sapien eget porttitor rhoncus, elit eros pulvinar mauris, at pharetra lectus ante ut mauris.</li>
    <li>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed semper, mauris non pulvinar ullamcorper, felis leo aliquet velit, vitae facilisis mi arcu eu elit. Pellentesque tincidunt, sapien eget porttitor rhoncus, elit eros pulvinar mauris, at pharetra lectus ante ut mauris.</li>
    <li>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed semper, mauris non pulvinar ullamcorper, felis leo aliquet velit, vitae facilisis mi arcu eu elit. Pellentesque tincidunt, sapien eget porttitor rhoncus, elit eros pulvinar mauris, at pharetra lectus ante ut mauris.</li>
</ol>


<div class="form-section">

    <%
        def ordinalSuffix
        switch( new Date().day ) {
            case 1:
            case 21:
            case 31:
                ordinalSuffix = "st"
                break
            case 2:
            case 22:
                ordinalSuffix = "nd"
                break
            case 3:
            case 33:
                ordinalSuffix = "rd"
                break
            default:
                ordinalSuffix = "th"
        }
    %>

    <p>This adoption contract is entered into this ${new Date().format("dd'${ordinalSuffix}' 'day of' MMMM, yyyy")}, between the
    organization FGT Inc. and the Adopter Not A.J. Brown.</p>

    <p><strong>Adoption Fee:</strong> $150.00</p>


    <div class="form-section">
        <div class="form-item" style="width: 420px;">
            <strong>Adopter's Address:</strong>
            <span class="value">1234 56th Alphabet Street, Dayton, Ohio 45123</span>
        </div>
        <div class="form-item" style="width: 200px;">
            <strong>Adopter's Phone:</strong>
            <span class="value">(937) 555-5555</span>
        </div>
    </div>

    <div class="signature-item" style="width: 420px;margin-right:8px;">
        <strong>Representative's Signature</strong>
    </div>
    <div class="signature-item" style="width: 200px;">
        <strong>Date Signed</strong>
    </div>


    <div class="signature-item" style="width: 420px;margin-right:8px;">
        <strong>Adopter's Signature</strong>
    </div>
    <div class="signature-item" style="width: 200px;">
        <strong>Date Signed</strong>
    </div>

</div>

<div id="footer"></div>

</body>
</html>
