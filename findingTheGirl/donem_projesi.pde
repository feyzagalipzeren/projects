PImage srcImg, template;
double maxValue = 0;

void setup()
{
  srcImg   = loadImage("srcImg.jpg");
  template = loadImage("template.jpg");
  
  int templateWidth  = template.width;
  int templateHeight = template.height;
  
  if( template.width % 2 == 0 )
    templateWidth--;
  if( template.height % 2 == 0 )
    templateHeight--;
  
  size( srcImg.width, srcImg.height );
  
  srcImg.filter(GRAY);
//  image( srcImg, 0, 0 );
  
  //double corr = getCorrCoeff( srcImg, srcImg );
  
  //println( corr );
  int size = templateWidth * templateHeight;
  color[] kernelPixels = new color[size];
  color[] templatePixels = new color[size];
  int counter;
  for( int x = templateWidth/2; x < srcImg.width - templateWidth/2; x++ )
  {
    for( int y = templateHeight/2; y < srcImg.height - templateHeight/2; y++ )
    {
      color centralPixel = (color) srcImg.pixels[ y * srcImg.width + x ];
      
     // double sumOfDiff = 0.0f;
     counter=0;
      
      for( int j = -templateWidth/2; j <= templateWidth/2; j++ )
      {
        for( int k = -templateHeight/2; k <= templateHeight/2; k++ )
        {
          kernelPixels[counter]   = (color) srcImg.pixels[ (y + k) * srcImg.width + (x + j) ];
          templatePixels[counter] = (color) template.pixels[ (k + templateHeight/2) * template.width + (j + templateWidth/2) ];
          //sumOfDiff += ( brightness( kernelPixel ) - brightness( templatePixel ) ) * ( brightness( kernelPixel ) - brightness( templatePixel ) );
          counter++;  
      }
      }
 
      double coeff = getCorrCoeff(kernelPixels,templatePixels);
      println("Coeff: "+coeff);
      int converted_coeff = ConvertRange(coeff);
      println("Converted Coeff: "+converted_coeff);
      srcImg.pixels[y*srcImg.width + x] = color(converted_coeff, converted_coeff, converted_coeff);
      
       if( coeff > maxValue )
      {
        maxValue = coeff;
        
        background( srcImg );
        strokeWeight( 2 );
        stroke( 255, 0, 0 );
        noFill();
        rect( x - templateWidth / 2, y - templateHeight / 2, templateWidth, templateHeight );
      }
      
      
    }
  }
  srcImg.updatePixels();
  image(srcImg, width, height);
  save("output.jpg");
  
}

double getCorrCoeff( color[] kPixels, color[] tPixels )
{
  
  double sumOfK      =    0.0f;
  double sumOfT      =    0.0f;
  
  for( int i = 0; i < kPixels.length; i++ )
  {
      color pixel1      =    kPixels[i];
      color pixel2      =    tPixels[i];
      
      sumOfK        +=    (double) brightness( pixel1 );
      sumOfT        +=    (double) brightness( pixel2 );
  }
  
  double meanOfK     =    sumOfK / kPixels.length;
  double meanOfT     =    sumOfT / tPixels.length;
  double sumOfSigma     =    0.0f;
  
  double denom = getStdDev( kPixels ) * getStdDev( tPixels );
  
  for( int i = 0; i < kPixels.length; i++ )
  {
      color pixel1      =    kPixels[i];
      color pixel2      =    tPixels[i];
      
      sumOfSigma       +=    ( ( (double) brightness( pixel1 ) - meanOfK ) * ( (double) brightness( pixel2 ) - meanOfT ) ) / denom;
  }
  
  double meanOfSigma    =    sumOfSigma     /    ( kPixels.length );
  
  return meanOfSigma; 
}

double getStdDev( color[] pix )
{
  double mean1 = 0.0f;

  for( int i = 0; i < pix.length; i++ )
  {
      color pixColor = pix[i];
      mean1 += (double) brightness( pixColor );
  }
  
  mean1 = mean1 / ( pix.length );
  
  double mean2 = 0.0f;
  
  for( int i = 0; i < pix.length; i++ )
  {
      color pixColor = pix[i];
      mean2 += ( Math.pow( (double) brightness( pixColor ) - mean1, 2 ) ); 
  }
  
  mean2 = mean2 / ( pix.length );

  return Math.sqrt( mean2 );
}

int ConvertRange(double number){
  
 int NewValue = (int)Math.round( (((number - (-1)) * (255 - 0)) / (1 - (-1))) + 0 );
 return NewValue; 
}
  
